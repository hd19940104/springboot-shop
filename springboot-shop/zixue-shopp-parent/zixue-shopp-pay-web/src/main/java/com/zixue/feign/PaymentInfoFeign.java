package com.zixue.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zixue.service.PaymentInfoService;


@FeignClient("pay-service")
public interface PaymentInfoFeign extends PaymentInfoService {

}
