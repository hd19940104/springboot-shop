package com.zixue.service;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.zixue.adapt.PayAdaptService;
import com.zixue.alipay.config.AlipayConfig;
import com.zixue.entity.PaymentInfo;
import com.zixue.entity.PaymentType;
/**
 * 支付宝接口调用
 * @author houdo
 *
 */
@Service
public class AliPayService implements PayAdaptService {

	@Override
	public String pay(PaymentInfo paymentInfo, PaymentType paymentType) {
		// 6.对接支付代码 返回提交支付from表单元素给客户端
				// 获得初始化的AlipayClient
				AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
						AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
						AlipayConfig.sign_type);

				// 设置请求参数
				AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
				alipayRequest.setReturnUrl(AlipayConfig.return_url);
				alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

				// 商户订单号，商户网站订单系统中唯一订单号，必填
				String out_trade_no = paymentInfo.getOrderId();
				// 付款金额，必填 企业金额
				String total_amount = paymentInfo.getPrice() + "";
				// 订单名称，必填
				String subject = "苹果手机xs MAX";
				// 商品描述，可空
				// String body = new
				// String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");

				alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount
						+ "\"," + "\"subject\":\"" + subject + "\","
						// + "\"body\":\""+ body +"\","
						+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

				// 若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
				// alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no
				// +"\","
				// + "\"total_amount\":\""+ total_amount +"\","
				// + "\"subject\":\""+ subject +"\","
				// + "\"body\":\""+ body +"\","
				// + "\"timeout_express\":\"10m\","
				// + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
				// 请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

				// 请求
			
					String result;
					try {
						result = alipayClient.pageExecute(alipayRequest).getBody();
					} catch (AlipayApiException e) {
						result="支付异常";
					}
					return result;
	}

}
