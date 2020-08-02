
package com.zixue.mq.distribute;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zixue.adapter.MessageAdapter;
import com.zixue.constants.MQInterfaceType;
import com.zixue.service.SMSMailboxService;
//import com.zixue.service.WeiXinMQService;
import com.zixue.service.WeiXinMQService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @classDesc: 功能描述:(消费消息 mq 从队列获取最新消息)
 */
@Slf4j
@Component
public class ConsumerDistribute {
	@Autowired
	private SMSMailboxService smsMailboxService;
	@Autowired
	private WeiXinMQService weiXinMQService;
	@JmsListener(destination = "mail_queue")
	public void distribute(String json) {
		try {
			log.info("###消息服务###收到消息,消息内容 json:{}", json);
			if (StringUtils.isEmpty(json)) {
				return;
			}
			JSONObject jsonObject = new JSONObject().parseObject(json);
			JSONObject header = jsonObject.getJSONObject("header");
			String interfaceType = header.getString("interfaceType");
			MessageAdapter messageAdapter = null;
			switch (interfaceType) {
			// 发送邮件
			case MQInterfaceType.SMS_MAIL:
				messageAdapter = smsMailboxService;
				break;
				// 发送邮件
			case MQInterfaceType.SMS_WEIXIN:
				messageAdapter = weiXinMQService;
				break;
			default:
				break;
			}
			JSONObject content = jsonObject.getJSONObject("content");
			messageAdapter.distribute(content);
		} finally {
			// TODO: handle finally clause
		}

	}

}
