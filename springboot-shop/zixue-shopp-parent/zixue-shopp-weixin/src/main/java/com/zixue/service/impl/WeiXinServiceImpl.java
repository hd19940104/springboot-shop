package com.zixue.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zixue.common.api.BaseApiService;
import com.zixue.service.WeiXinService;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Slf4j
@RestController
@RequestMapping("/weixin")
public class WeiXinServiceImpl extends BaseApiService implements WeiXinService {
	@Autowired
	private WxMpService wxService;

	@Override
	public Map<String, Object> sendTemplate(@RequestBody WxMpTemplateMessage wxMpTemplateMessage) {
		try {
			WxMpTemplateMsgService templateMsgService = wxService.getTemplateMsgService();
			String sendTemplateMsg = templateMsgService.sendTemplateMsg(wxMpTemplateMessage);
			return setResutSuccessData(sendTemplateMsg);
		} catch (Exception e) {
			log.error("WeiXinServiceImpl()###ERROR:", e);
			return setResutError(e.getMessage());
		}

	}

}
