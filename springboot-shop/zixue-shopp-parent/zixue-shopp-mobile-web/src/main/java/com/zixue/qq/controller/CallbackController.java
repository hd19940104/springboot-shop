//
//package com.zixue.qq.controller;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.qq.connect.QQConnectException;
//import com.qq.connect.api.OpenID;
//import com.qq.connect.javabeans.AccessToken;
//import com.qq.connect.oauth.Oauth;
//import com.zixue.base.controller.BaseController;
//import com.zixue.constants.BaseApiConstants;
//import com.zixue.constants.Constants;
//import com.zixue.entity.UserEntity;
//import com.zixue.feign.UserFeign;
//import com.zixue.web.CookieUtil;
//
//@Controller
//@lombok.extern.slf4j.Slf4j
//public class CallbackController extends BaseController {
//	private static final String LGOIN = "login";
//	private static final String INDEX = "index";
//	private static final String ERROR = "common/error";
//	private static final String ASSOCIATEDACCOUNT = "associatedAccount";
//
//
//	/**
//	 * 
//	 * @methodDesc: 功能描述:(生成qq授权)
//	 * @param: @param
//	 *             request
//	 * @param: @return
//	 * @param: @throws
//	 *             QQConnectException
//	 */
//	@RequestMapping("/authorizeUrl")
//	public String authorizeUrl(HttpServletRequest request, HttpServletResponse response,HttpSession  httpSession) throws QQConnectException {
//		String authorizeUrl;
//		try {
//			authorizeUrl = new Oauth().getAuthorizeURL(request);
////			authorizeUrl="http://127.0.0.1:9503/mobile/qqLoginCallback";
//			return "redirect:" + authorizeUrl;
//		} catch (Exception e) {
//			log.info("###出现异常");
//			return ERROR;
//			
//		}
//	
//	}
//
//	@RequestMapping("/qqLoginCallback")
//	public String qqLoginCallback(HttpServletRequest request, HttpServletResponse response,HttpSession  httpSession) throws QQConnectException {
//		// String code = request.getParameter("code");
//		// 第一步获取授权码
//		// 第二步获取accesstoken
//		try {
//			AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(request);
//			if (accessTokenObj == null) {
//				return setError(request, "qq授权失败!", ERROR);
//			}
//			String accessToken = accessTokenObj.getAccessToken();
//			if (StringUtils.isEmpty(accessToken)) {
//				return setError(request, "QQ授权失败!", ERROR);
//			}
//
//			OpenID openidObj = new OpenID(accessToken);
//			// 数据查找openid是否关联,如果没有关联先跳转到关联账号页面,如果直接登录.
//			String userOpenId = openidObj.getUserOpenID();
//			Map<String, Object> userLoginOpenId = userFeign.userLoginOpenId(userOpenId);
//			Integer code = (Integer) userLoginOpenId.get(BaseApiConstants.HTTP_CODE_NAME);
//			if (code.equals(BaseApiConstants.HTTP_200_CODE)) {
//				String token = (String) userLoginOpenId.get("data");
//				CookieUtil.addCookie(response, Constants.USER_TOKEN, token, Constants.WEBUSER_COOKIE_TOKEN_TERMVALIDITY);
//				return "redirect:/" + INDEX;
//			}
//			
//			// 没有关联QQ账号
//			httpSession.setAttribute(Constants.USER_SESSION_OPENID,userOpenId);
//			return ASSOCIATEDACCOUNT;
//		} catch (Exception e) {
//			log.info("###测试系统异常");
//			return LGOIN;
//			
//		}
//
//	}
//}
