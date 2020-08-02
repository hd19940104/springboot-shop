package com.zixue.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zixue.service.ItemService;


@FeignClient("commodity")
public interface ItemFeign extends ItemService {

}
