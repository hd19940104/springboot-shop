package com.zixue.service;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/demo")
public interface DemoApiService {
	
	/**
	 * 服务demo
	 * @return
	 */
	@GetMapping("/demo")
	public Map<String, Object> demo();
	@GetMapping("/setKey")
	Map<String, Object> setKey( String key, String value);
	@GetMapping("/getKey")
	Map<String, Object> getKey(String key);
}
