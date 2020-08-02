package com.zixue.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.zixue.common.api.BaseApiService;
import com.zixue.common.redis.BaseRedisService;
import com.zixue.service.DemoApiService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DemoApiServiceImpl extends BaseApiService implements DemoApiService {
	@Autowired
	private BaseRedisService baseRedisService;

	@Override
	public Map<String, Object> demo() {
		return setResutSuccess();
	}

	@Override
	public Map<String, Object> setKey(String key, String value) {
		baseRedisService.setString(key, value);
		return setResutSuccess();

	}

	@Override
	public Map<String, Object> getKey(String key) {
		String value = baseRedisService.get(key);
		return setResutSuccessData(value);

	}
}
