package com.zixue.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zixue.service.ItemDescService;


@FeignClient("commodity")
public interface ItemDescFeign extends ItemDescService {

}
