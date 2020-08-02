package com.zixue.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zixue.service.PaymentTypeService;


@FeignClient("pay-service")
public interface PaymentTypeFeign extends PaymentTypeService {

}
