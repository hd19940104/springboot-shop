
package com.zixue.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zixue.service.UserService;

@FeignClient("member")
public interface UserFeign extends UserService {

}
