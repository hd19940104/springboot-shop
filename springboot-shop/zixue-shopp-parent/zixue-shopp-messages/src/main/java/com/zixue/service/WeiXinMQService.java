package com.zixue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zixue.adapter.MessageAdapter;
import com.zixue.constants.BaseApiConstants;
import com.zixue.entity.UserEntity;
import com.zixue.feign.UserFeign;
import com.zixue.feign.WeiXinFeign;
import com.zixue.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Slf4j
@Service
public class WeiXinMQService implements MessageAdapter {
	@Autowired
    UserFeign userFeign;
	@Autowired
	WeiXinFeign weiXinFeign;
	@Override
	public void distribute(JSONObject jsonObject) {
      //使用userid查找用户openid
		Long userId=jsonObject.getLong("userId");
		Map<String, Object> resultMap = userFeign.getUserId(userId);
		Integer code = (Integer) resultMap.get(BaseApiConstants.HTTP_CODE_NAME);
		if (!code.equals(BaseApiConstants.HTTP_200_CODE)) {
			return ;
		}
	  //获取到openid 在调用微信接口
		Map<String, Object> mapItem = (Map<String, Object>) resultMap.get("data");
		String json = new JSONObject().toJSONString(mapItem);
		UserEntity userEntity = new JSONObject().parseObject(json, UserEntity.class);
		String weixinOpenId = userEntity.getWeixinOpenId();
		if (StringUtils.isEmpty(weixinOpenId)) {
			log.error("distribute() weixinOpenId is null ");
			return;
		}
		// 调用微信接口
		WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
		wxMpTemplateMessage.setToUser(weixinOpenId);
		wxMpTemplateMessage.setUrl("http://lovedongdong.natapp1.cc");
		wxMpTemplateMessage.setTemplateId("zo2fbfhItFsL2CrU1AjPw4hLaljmp76elHO55kFYZM2");
		List<WxMpTemplateData> data = new ArrayList<>();
		// 设置时间
		WxMpTemplateData wxMpTemplateData1 = new WxMpTemplateData();
		wxMpTemplateData1.setName("first");
		wxMpTemplateData1.setValue(DateUtils.currentFormatDate(DateUtils.DATE_TO_STRING_DETAIAL_PATTERN));
		wxMpTemplateData1.setColor("#173177");
		// 时间价格
		WxMpTemplateData wxMpTemplateData2 = new WxMpTemplateData();
		wxMpTemplateData2.setName("keyword1");
		Double price = jsonObject.getDouble("price");
		wxMpTemplateData2.setValue(price + "");
		wxMpTemplateData2.setColor("#173177");
		// 设置订单号
		WxMpTemplateData wxMpTemplateData3 = new WxMpTemplateData();
		wxMpTemplateData3.setName("keyword2");
		String orderId = jsonObject.getString("orderId");
		wxMpTemplateData3.setValue(orderId);
		wxMpTemplateData3.setColor("#173177");
		data.add(wxMpTemplateData1);
		data.add(wxMpTemplateData2);
		data.add(wxMpTemplateData3);
		wxMpTemplateMessage.setData(data);
		weiXinFeign.sendTemplate(wxMpTemplateMessage);

	}

}
