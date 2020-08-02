package com.zixue.weixin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zixue.constants.BaseApiConstants;
import com.zixue.constants.Constants;
import com.zixue.feign.UserFeign;
import com.zixue.web.CookieUtil;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Controller
public class CallbackController {
	private static final String ERROR = "error";
	private static final String INDEX = "index";
	private static final String ASSOCIATEDACCOUNT = "associatedAccount";

	@Autowired
	private WxMpService wxService;
	@Autowired
	private UserFeign userFeign;

	@RequestMapping("/weixinCallback")
	public String weixinCallback(String code,HttpServletResponse response, HttpSession httpSession) throws WxErrorException {
		// 步骤
		// 使用code换取token
		WxMpOAuth2AccessToken oauth2getAccessToken = wxService.oauth2getAccessToken(code);
		// 使用token换取用户信息
		WxMpUser oauth2getUserInfo = wxService.oauth2getUserInfo(oauth2getAccessToken, "zh_CN");
		// 判断是否已经授权过,如果没有授权
		String weixinOpenId = oauth2getUserInfo.getOpenId();
		Map<String, Object> reusltWeiXinOpenId= userFeign.findWeiXinOpenId(weixinOpenId);
		// 拿到用户信息，进行关联
		Integer resultCode = (Integer) reusltWeiXinOpenId.get(BaseApiConstants.HTTP_CODE_NAME);
		// 判断是否绑定会员
		if (resultCode.equals(BaseApiConstants.HTTP_200_CODE)) {
			// 已经授权过,自动登录
			String token = (String) reusltWeiXinOpenId.get("data");
			CookieUtil.addCookie(response, Constants.USER_TOKEN, token, Constants.WEBUSER_COOKIE_TOKEN_TERMVALIDITY);
			return INDEX;
		}
		httpSession.setAttribute("weixinOpenId", weixinOpenId);
		httpSession.setAttribute("source", "weixin");
		return ASSOCIATEDACCOUNT;
	}

}
