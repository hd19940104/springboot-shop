
package com.zixue.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.qq.connect.QQConnectException;
//import com.qq.connect.api.OpenID;
//import com.qq.connect.javabeans.AccessToken;
//import com.qq.connect.oauth.Oauth;
import com.zixue.base.controller.BaseController;
import com.zixue.constants.BaseApiConstants;
import com.zixue.constants.Constants;
import com.zixue.entity.UserEntity;
import com.zixue.feign.UserFeign;
import com.zixue.web.CookieUtil;

@Controller
public class LoginConroller extends BaseController {
	private static final String LGOIN = "login";
	private static final String INDEX = "index";
	private static final String ERROR = "common/error";
	private static final String ASSOCIATEDACCOUNT = "associatedAccount";
	@Autowired
	private UserFeign userFeign;

	@RequestMapping("/locaLogin")
	public String locaLogin(String source,HttpServletRequest request) {
		request.setAttribute("source", source);
		return LGOIN;
	}

	@RequestMapping("/login")
	public String login(@ModelAttribute("user")UserEntity userEntity,String source, HttpServletRequest request,HttpSession httpSession, HttpServletResponse response) {
		if(!StringUtils.isEmpty(source)){
			if(source.equals("qq")){
				String openid = (String) httpSession.getAttribute("openid");
				userEntity.setOpenId(openid);
			}
			
			if(source.equals("weixin")){
				String weixinOpenId = (String) httpSession.getAttribute("weixinOpenId");
				userEntity.setWeixinOpenId(weixinOpenId);
			}
		}
		Map<String, Object> login = userFeign.login(userEntity);
		Integer code = (Integer) login.get(BaseApiConstants.HTTP_CODE_NAME);
		if (!code.equals(BaseApiConstants.HTTP_200_CODE)) {
			String msg = (String) login.get("msg");
			return setError(request, msg, LGOIN);
		}
		// 登录成功之后,获取token.将这个token存放在cookie
		String token = (String) login.get("data");
		CookieUtil.addCookie(response, Constants.USER_TOKEN, token, Constants.WEBUSER_COOKIE_TOKEN_TERMVALIDITY);
		return INDEX;
	}

}
