package com.zixue.controller.callback;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import com.zixue.base.controller.BaseController;
import com.zixue.service.CallbackService;

@RequestMapping("/yinlianpay/callback")
@Controller
public class YinLianCallbackController extends BaseController{
	@Autowired
	@Qualifier("YinLianCallbackServiceImpl")
	private CallbackService callbackService;
	private static final String PAY_SUCCESS = "pay_success";
	private static final String PAY_FAIL = "pay_fail";

	@RequestMapping("/syn")
	public String syn(HttpServletRequest req) {
		Map<String, String> valideData = callbackService.syn(req);
		// // 重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		String encoding = req.getParameter(SDKConstants.param_encoding);
		if (!AcpService.validate(valideData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			// 验签失败，需解决验签问题
			return PAY_FAIL;
		}
		req.setAttribute("txnAmt", Double.parseDouble(valideData.get("txnAmt")) / 100);
		req.setAttribute("orderId", Long.parseLong(valideData.get("orderId")));
		LogUtil.writeLog("验证签名结果[成功].");
		return PAY_SUCCESS;

	}

	@RequestMapping("/asyn")
	public String asyn(HttpServletRequest req) {
		return callbackService.asyn(req);

	}

}
