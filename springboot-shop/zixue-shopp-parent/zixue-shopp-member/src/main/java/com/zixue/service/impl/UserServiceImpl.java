
package com.zixue.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zixue.common.api.BaseApiService;
import com.zixue.entity.UserEntity;
import com.zixue.manage.UserServiceManage;
import com.zixue.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserServiceImpl extends BaseApiService implements UserService {
	@Autowired
	private UserServiceManage userServiceManage;

	@Override
	public Map<String, Object> regist(@RequestBody UserEntity userEntity) {
		if (StringUtils.isEmpty(userEntity.getUserName())) {
			return setResutParameterError("用户名称不能为空!");
		}
		if (StringUtils.isEmpty(userEntity.getPassword())) {
			return setResutParameterError("密码不能为空!");
		}
		try {
			userServiceManage.regist(userEntity);
			return setResutSuccess();
		} catch (Exception e) {
			log.error("###regist() ERRPR:", e);
			return setResutError("注册失败!");
		}
	}

	@Override
	public Map<String, Object> login(@RequestBody UserEntity userEntity) {
		return userServiceManage.login(userEntity);
	}

	@Override
	public Map<String, Object> getUser(@RequestParam("token") String token) {
		if (StringUtils.isEmpty(token)) {
			return setResutParameterError("token不能为空!");
		}
		return userServiceManage.getUser(token);

	}
	@Override
	public Map<String, Object> userLoginOpenId(@RequestParam("openid")String openid) {
		return userServiceManage.userLoginOpenId(openid);  
		    
	}
	@Override
	public Map<String, Object> findWeiXinOpenId(String weixinOpenId) {
	
		return userServiceManage.findWeiXinOpenId(weixinOpenId);
	}

	@Override
	public Map<String, Object> getUserId(Long userId) {
		return userServiceManage.getUserId(userId);
	}

}
