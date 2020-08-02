package com.zixue.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@RequestMapping("/weixin")
public interface WeiXinService {

	@RequestMapping("/sendTemplate")
	 public Map<String, Object>  sendTemplate(@RequestBody WxMpTemplateMessage wxMpTemplateMessage);
	
}
