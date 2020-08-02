package com.zixue.controller.callback;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zixue.base.controller.BaseController;
import com.zixue.service.CallbackService;
@RequestMapping("/alipay/callback")
@Controller

public class AliPayCallbackController extends BaseController {
	@Autowired
	@Qualifier("AliPayCallbackServiceImpl")
	private CallbackService callbackService;
	@RequestMapping("/returnUrl")
	public String returnUrl(HttpServletRequest req,@RequestParam Map<String, String> params)  {
		return callbackService.returnUrl(req, params);
		
	}

	@RequestMapping("/notifyUrl")
	public String notifyUrl(@RequestParam Map<String, String> params) {
		return callbackService.notifyUrl(params);
	}
}
