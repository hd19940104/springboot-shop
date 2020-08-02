package com.zixue.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zixue.adapt.PayAdaptService;
import com.zixue.entity.PaymentInfo;
import com.zixue.entity.PaymentType;
import com.zixue.feign.PaymentTypeFeign;
import com.zixue.service.AliPayService;
import com.zixue.service.PayService;
import com.zixue.service.YinLianPayService;
import com.zixue.utils.ResultUtils;

/**
 * 代码重建
 * @author houdo
 *
 */
@Service
public class PayServiceImpl implements PayService {
	@Autowired
	private PaymentTypeFeign paymentTypeFeign;
	@Autowired
	private YinLianPayService yinLianPayService;
	@Autowired
	private AliPayService aliPayService;
	@Override
	public String pay(PaymentInfo paymentInfo) {
		// 判断支付类型
		Long typeId = paymentInfo.getTypeId();
		Map<String, Object> getPaymentTypeMap = paymentTypeFeign.getPaymentType(typeId);
		Map<String, Object> resultMap = (Map<String, Object>) ResultUtils.getResultMap(getPaymentTypeMap);
		String json = new JSONObject().toJSONString(resultMap);
		PaymentType paymentType = new JSONObject().parseObject(json, PaymentType.class);
		if (paymentType == null) {
			return null;
		}
		String typeName=paymentType.getTypeName();
		PayAdaptService payAdaptService=null;
		switch (typeName) {
		case "yinlianPay":
			payAdaptService=yinLianPayService;
			break;
		case "aliPay":
			payAdaptService=aliPayService;
			break;
		default:
			break;
		}

		return payAdaptService.pay(paymentInfo, paymentType);
	}

}
