package com.zixue.service;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zixue.entity.UserEntity;

/**
 * 
 * @author houdo
 *
 */
@RequestMapping("/member")
public interface UserService {
	/**
	 *
	 * @methodDesc: 功能描述:(注册服务)
	 * @param: @param
	 *             UserEntity
	 * @param: @return
	 */
	@PostMapping("/regist")
	public Map<String, Object> regist(@RequestBody UserEntity UserEntity);

	/**
	 * 功能描述:登录成功后,生成对应的token，作为key,将用户userID作为value存放在redis中.返回token给客户端.
	 * 
	 * @methodDesc: 功能描述:(登录)
	 * @param: @return
	 */
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody UserEntity userEntity);

	/**
	 * 
	 * @methodDesc: 功能描述:(使用token查找用户信息)
	 * @param: @param
	 *             token
	 * @param: @return
	 */
	@PostMapping("/getUser")
	public Map<String, Object> getUser(@RequestParam("token") String token);
	
	/**
	 * 
	 * @methodDesc: 功能描述:(使用openid关联用户信息)
	 * @param: @param
	 *             token
	 * @param: @return
	 */
	@PostMapping("/userLoginOpenId")
	public Map<String, Object> userLoginOpenId(@RequestParam("openid") String openid);
	@PostMapping("/findWeiXinOpenId")
	public Map<String, Object> findWeiXinOpenId(@RequestParam("weixinOpenId") String weixinOpenId);

	@PostMapping("/getUserId")
	public Map<String, Object> getUserId(@RequestParam("userId") Long userId);

}
