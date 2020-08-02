
package com.zixue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.zixue.dao")
public class MemberServer {

	public static void main(String[] args) {
		SpringApplication.run(MemberServer.class, args);
	}

}
