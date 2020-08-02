package com.zixue.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;

public interface CallbackService {
	/**
	 * 同步回调
	 * 
	 * @return
	 */
	public Map<String, String> syn(HttpServletRequest request);

	/**
	 * 异步回调
	 * 
	 * @return
	 */
	public String asyn(HttpServletRequest request);
	/**
	 * 支付宝异步
	 * @param params
	 * @return
	 */
	public String notifyUrl(@RequestParam Map<String, String> params);
	/**
	 * 支付宝同步
	 * @param params
	 * @return
	 */
	public String returnUrl(HttpServletRequest req,@RequestParam Map<String, String> params);
	
}
