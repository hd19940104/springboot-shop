package com.zixue.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.zixue.alipay.config.AlipayConfig;
import com.zixue.base.controller.BaseController;
import com.zixue.constants.Constants;
import com.zixue.entity.PaymentInfo;
import com.zixue.feign.PaymentInfoFeign;
import com.zixue.mq.roducer.RegisterMailboxProducer;
import com.zixue.service.CallbackService;
import com.zixue.utils.ResultUtils;

@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Service("AliPayCallbackServiceImpl")
public class AliPayCallbackServiceImpl extends BaseController implements CallbackService {
	@Autowired
	private PaymentInfoFeign paymentInfoFeign;
	@Value("${messages.queue}")
	private String MESSAGES_QUEUE;
	@Autowired
	private RegisterMailboxProducer registerMailboxProducer;
	private static final String PAY_SUCCESS = "pay_success";
	private static final String PAY_FAIL = "pay_fail";

	@Override
	public String notifyUrl(Map<String, String> params) {
		// 1.日志记录
		log.info("#####支付宝异步通知synCallBack#####开始,params:{}", params);
		// 2.验签操作
		try {
			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
					AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名
			log.info("#####支付宝异步通知signVerified:{}######", signVerified);
			// ——请在这里编写您的程序（以下代码仅作参考）——
			if (!signVerified) {
				return Constants.PAY_FAIL;
			}
			// 商户订单号
			String outTradeNo = params.get("out_trade_no");
			Map<String, Object> orderIdPayInfo = paymentInfoFeign.getByOrderIdPayInfo(outTradeNo);
			Map<String, Object> resultMap = (Map<String, Object>) ResultUtils.getResultMap(orderIdPayInfo);
			String json = new JSONObject().toJSONString(resultMap);
			PaymentInfo paymentInfo = new JSONObject().parseObject(json, PaymentInfo.class);
			if (paymentInfo == null) {
				return Constants.PAY_FAIL;
			}

			// 支付宝重试机制
			Integer state = paymentInfo.getState();
			if (state == 1) {
				return Constants.PAY_SUCCESS;
			}

			// 支付宝交易号
			String tradeNo = params.get("trade_no");
			// 付款金额
			// String totalAmount = params.get("total_amount");
			// 判断实际付款金额与商品金额是否一致
			// 修改 支付表状态
			paymentInfo.setState(1);// 标识为已经支付
			paymentInfo.setPayMessage(params.toString());
			paymentInfo.setPlatformorderId(tradeNo);
			// 手动 begin begin
			paymentInfoFeign.updatePayInfo(paymentInfo);

			// 调用订单接口通知 支付状态
			/*
			 * ResponseBase orderResult = paymentInfoFeign.updateOrderIdInfo(1l, tradeNo,
			 * outTradeNo); if
			 * (!orderResult.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) { // 回滚 手动回滚
			 * rollback return Constants.PAY_FAIL; } // 2PC 3PC TCC MQ
			 */ // 手动 提交 comiit;
			return Constants.PAY_SUCCESS;
		} catch (Exception e) {
			log.error("####支付宝异步通知出现异常,ERROR:", e);
			// 回滚 手动回滚 rollback
			return Constants.PAY_FAIL;
		} finally {
			log.info("#####支付宝异步通知synCallBack#####结束,params:{}", params);
		}
	}

	@Override
	public String returnUrl(HttpServletRequest req, Map<String, String> params) {
		// 1.日志记录
		log.info("#####支付宝同步通知synCallBack#####开始,params:{}", params);
		// 2.验签操作
		try {
			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
					AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名
			log.info("#####支付宝同步通知signVerified:{}######", signVerified);
			// ——请在这里编写您的程序（以下代码仅作参考）——
			if (!signVerified) {
				// return setResutError("验签失败!");
				return PAY_FAIL;
			}
			// 商户订单号
			String outTradeNo = params.get("out_trade_no");
			// 支付宝交易号
			String tradeNo = params.get("trade_no");
			// 付款金额
			String totalAmount = params.get("total_amount");
			// JSONObject data = new JSONObject();
			// data.put("outTradeNo", outTradeNo);
			// data.put("tradeNo", tradeNo);
			// data.put("totalAmount", totalAmount);
			// return setResutSuccessData(data);
			req.setAttribute("txnAmt", totalAmount);
			req.setAttribute("orderId", outTradeNo);
			return PAY_SUCCESS;
		} catch (Exception e) {
			log.error("####支付宝同步通知出现异常,ERROR:", e);
			log.info("系统错误!");
			return PAY_FAIL;
		} finally {
			log.info("#####支付宝同步通知synCallBack#####结束,params:{}", params);
		}
	}

	@Override
	public Map<String, String> syn(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String asyn(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
