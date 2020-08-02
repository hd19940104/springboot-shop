package com.zixue.service.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import com.zixue.constants.Constants;
import com.zixue.constants.MQInterfaceType;
import com.zixue.entity.PaymentInfo;
import com.zixue.feign.PaymentInfoFeign;
import com.zixue.mq.roducer.RegisterMailboxProducer;
import com.zixue.service.CallbackService;
import com.zixue.utils.DateUtils;
import com.zixue.utils.ResultUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("YinLianCallbackServiceImpl")
public class YinLianCallbackServiceImpl implements CallbackService {
	@Autowired
	private PaymentInfoFeign paymentInfoFeign;
	@Value("${messages.queue}")
	private String MESSAGES_QUEUE;
	@Autowired
	private RegisterMailboxProducer registerMailboxProducer;

	@Override
	public Map<String, String> syn(HttpServletRequest req) {

		LogUtil.writeLog("YinLianCallbackServiceImpl接收到前台通知开始");
		String encoding = req.getParameter(SDKConstants.param_encoding);
		Map<String, String> valideData = valideData(req, encoding);

		LogUtil.writeLog("YinLianCallbackServiceImpl接收到前台通知结束");
		return valideData;
	}

	@Override
	public String asyn(HttpServletRequest req) {
		String encoding = req.getParameter(SDKConstants.param_encoding);
		Map<String, String> valideData = valideData(req, encoding);
		// 重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(valideData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			// 验签失败，需解决验签问题

		}
		LogUtil.writeLog("验证签名结果[成功].");
		// 【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态

		String orderId = valideData.get("orderId"); // 获取后台通知的数据，其他字段也可用类似方式获取
		Map<String, Object> byOrderIdPayInfo = paymentInfoFeign.getByOrderIdPayInfo(orderId);
		Map<String, Object> resultMap = (Map<String, Object>) ResultUtils.getResultMap(byOrderIdPayInfo);
		String json = new JSONObject().toJSONString(resultMap);
		PaymentInfo paymentInfo = new JSONObject().parseObject(json, PaymentInfo.class);
		if (paymentInfo == null) {
			return Constants.PAY_FAIL;
		}

		Integer state = paymentInfo.getState();
		if (state.equals(1)) {
			log.error("订单号:{},已经支付成功!,无需再次做操作..");
			return Constants.PAY_SUCCES;
		}
		// 第三方支付订单号
		paymentInfo.setPlatformorderId(valideData.get("queryId"));
		// 修改時間
		paymentInfo.setUpdated(DateUtils.getTimestamp());
		// 支付报文
		paymentInfo.setPayMessage(new JSONObject().toJSONString(valideData));
		// 状态
		paymentInfo.setState(1);
		paymentInfoFeign.updatePayInfo(paymentInfo);
		// 金额 调用中石油充值支付接口--- 延迟 10 15
		// 异步 mq http协议
		// mq推送支付成功消息
		Double price = (double) (paymentInfo.getPrice() / 100);
		String mqWeiXinJson = weixinMessage(paymentInfo.getUserId(), price, paymentInfo.getOrderId());
		// 队列
		Destination activeMQQueue = new ActiveMQQueue(MESSAGES_QUEUE);
		log.info("###asyn() 支付发微信推送报文mqWeiXinJson:{}", mqWeiXinJson);
		// mq
		registerMailboxProducer.send(activeMQQueue, mqWeiXinJson);
		return "ok";
	}

	private String weixinMessage(Long userId, Double price, String orderId) {
		JSONObject root = new JSONObject();
		JSONObject header = new JSONObject();
		header.put("interfaceType", MQInterfaceType.SMS_WEIXIN);
		JSONObject content = new JSONObject();
		content.put("userId", userId);
		content.put("price", price);
		content.put("orderId", orderId);
		root.put("header", header);
		root.put("content", content);
		return root.toJSONString();
	}

	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				// System.out.println("ServletUtil类247行 temp数据的键=="+en+"
				// 值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}

	public static Map<String, String> valideData(HttpServletRequest req, String encoding) {

		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(req);

		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				try {
					value = new String(value.getBytes(encoding), encoding);
				} catch (Exception e2) {
					// TODO: handle exception
				}
				valideData.put(key, value);
			}
		}
		return valideData;
	}

	@Override
	public String notifyUrl(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnUrl(HttpServletRequest req,Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}
}
