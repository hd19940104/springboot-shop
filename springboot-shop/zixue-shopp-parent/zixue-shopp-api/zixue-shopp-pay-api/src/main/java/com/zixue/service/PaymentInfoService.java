package com.zixue.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zixue.entity.PaymentInfo;


/**
 * 功能描述,提供支付信息接口服务
 *
 */
@RequestMapping("/pay")
public interface PaymentInfoService {

	/**
	 * 创建支付交易信息
	 * 
	 * @return
	 */
	@RequestMapping("/addPayInfoToken")
	public Map<String, Object> addPayInfoToken(@RequestBody PaymentInfo paymentInfo);
	/**
	 * 使用token获取支付信息
	 * @param token
	 * @return
	 */
	@RequestMapping("/getPayInfoToken")
	public Map<String, Object> getPayInfoToken(@RequestParam("token") String token);
	
	
	/**
	 * 使用订单号查找支付信息
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/getByOrderIdPayInfo")
	public Map<String, Object> getByOrderIdPayInfo(@RequestParam("orderId") String orderId);
    /**
     * 修改支付信息
     * @param paymentInfo
     * @return
     */
	@RequestMapping("/updatePayInfo")
	public Map<String, Object> updatePayInfo(@RequestBody PaymentInfo paymentInfo);
 
	
}
