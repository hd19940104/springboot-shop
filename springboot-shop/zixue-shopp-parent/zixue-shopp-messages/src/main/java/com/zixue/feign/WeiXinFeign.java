package com.zixue.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zixue.service.WeiXinService;

@FeignClient(name = "weixin-service")
public interface WeiXinFeign  extends WeiXinService{
}
