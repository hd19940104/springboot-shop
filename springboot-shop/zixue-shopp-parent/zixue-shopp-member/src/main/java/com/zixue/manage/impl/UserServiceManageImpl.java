
package com.zixue.manage.impl;

import java.util.Map;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zixue.common.api.BaseApiService;
import com.zixue.common.redis.BaseRedisService;
import com.zixue.common.token.TokenUtils;
import com.zixue.constants.Constants;
import com.zixue.constants.DBTableName;
import com.zixue.constants.MQInterfaceType;
import com.zixue.dao.UserDao;
import com.zixue.entity.UserEntity;
import com.zixue.manage.UserServiceManage;
import com.zixue.mq.roducer.RegisterMailboxProducer;
import com.zixue.utils.DateUtils;
import com.zixue.utils.MD5Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceManageImpl extends BaseApiService implements UserServiceManage {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RegisterMailboxProducer registerMailboxProducer;
	@Value("${messages.queue}")
	private String MESSAGES_QUEUE;
	@Autowired
	private TokenUtils tokenUtils;
	@Autowired
	private BaseRedisService baseRedisService;

	/**
	 * 会员注册
	 */
	@Override
	public void regist(UserEntity userEntity) {
		userEntity.setCreated(DateUtils.getTimestamp());
		userEntity.setUpdated(DateUtils.getTimestamp());
		String phone = userEntity.getPhone();
		String password = userEntity.getPassword();
		userEntity.setPassword(md5PassSalt(phone, password));
		userDao.save(userEntity, DBTableName.TABLE_MB_USER);
		// 队列
		Destination activeMQQueue = new ActiveMQQueue(MESSAGES_QUEUE);
		// 组装报文格式
		String mailMessage = mailMessage(userEntity.getEmail(), userEntity.getUserName());
		log.info("###regist() 注册发送邮件报文mailMessage:{}", mailMessage);
		// mq
		registerMailboxProducer.send(activeMQQueue, mailMessage);
	}

	/**
	 * md5+加盐
	 */
	@Override
	public String md5PassSalt(String phone, String password) {
		String newPass = MD5Util.MD5(phone + password);
		return newPass;

	}

	/**
	 * 组装邮箱报文格式
	 * 
	 * @param email
	 * @param userName
	 * @return
	 */
	private String mailMessage(String email, String userName) {
		JSONObject root = new JSONObject();
		JSONObject header = new JSONObject();
		header.put("interfaceType", MQInterfaceType.SMS_MAIL);
		JSONObject content = new JSONObject();
		content.put("mail", email);
		content.put("userName", userName);
		root.put("header", header);
		root.put("content", content);
		return root.toJSONString();
	}

	@Override
	public Map<String, Object> login(UserEntity userEntity) {
		// 往数据进行查找数据
		String phone = userEntity.getPhone();
		String password = userEntity.getPassword();
		String newPassWord = md5PassSalt(phone, password);
		log.info(phone + "|" + password + "|" + newPassWord);
		UserEntity userPhoneAndPwd = userDao.getUserPhoneAndPwd(phone, newPassWord);
		if (userPhoneAndPwd == null) {
			return setResutError("账号或密码错误");
		}
		String openId = userEntity.getOpenId();
		Long userId = userPhoneAndPwd.getId();
		if (!StringUtils.isEmpty(openId)) {
			// 修改到数据库
			// userDao.updateUserOpenId(openId, DateUtils.getTimestamp(),
			// userId);
			UserEntity user = new UserEntity();
			user.setOpenId(openId);
			user.setUpdated(DateUtils.getTimestamp());
			userDao.update(user, DBTableName.TABLE_MB_USER, userId);
		}
		// 生成对应的token
		String token = setUsertoken(userId);
		return setResutSuccessData(token);

	}

	@Override
	public Map<String, Object> getUser(String token) {
		// 从redis中查找到userid
		String userId = baseRedisService.get(token);
		if (StringUtils.isEmpty(userId)) {
			return setResutError("用户已经过期!");
		}
		Long newUserIdl = Long.parseLong(userId);
		UserEntity userInfo = userDao.getUserInfo(newUserIdl);
		userInfo.setPassword(null);
		return setResutSuccessData(userInfo);
	}

	@Override
	public Map<String, Object> userLoginOpenId(String openid) {
		UserEntity userEntity = userDao.findUserOpenId(openid);
		if (userEntity == null) {
			return setResutError("没有关联用户");
		}
		// 生成对应的token
		String token = setUsertoken(userEntity.getId());
		return setResutSuccessData(token);

	}
	/**
	 * 创建token令牌保存redis
	 * @param id
	 * @return
	 */
	private String setUsertoken(Long id) {
		String token = tokenUtils.getToken();
		// key为自定义令牌,用户的userId作作为value 存放在redis中
		baseRedisService.set(token, id + "", Constants.USER_TOKEN_TERMVALIDITY);
		return token;
	}
	/**
	 * 查询微信openid
	 */
	@Override
	public Map<String, Object> findWeiXinOpenId(String openid) {
		UserEntity userEntity = userDao.findWeiXinOpenId(openid);
		if (userEntity == null) {
			return setResutError("没有查找到该信息.");
		}
		// 自动登录
		String token = setUsertoken(userEntity.getId());
		return setResutSuccessData(token);
	}

	/**
	 * 查询用户信息
	 */
	@Override
	public Map<String, Object> getUserId(Long userId) {
		UserEntity userInfo = userDao.getUserInfo(userId);
		if (userInfo == null) {
			return setResutError("没有查找到该信息.");
		}
		return setResutSuccessData(userInfo);
	}
}
