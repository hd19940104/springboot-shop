
package com.zixue.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zixue.base.controller.BaseController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DemoController  extends BaseController {
	// index页面
	public static final String INDEX = "index";

	@RequestMapping("/index11")
	public String index(HttpServletRequest request,String token) {
		log.info(" 我的web工程搭建成功啦!,userName:{}",getUserEntity(token).getUserName());
		return INDEX;
	}
	@RequestMapping("/index1")
	public String index() {
		log.info(" 我的web工程搭建成功啦!,userName:{}");
		return INDEX;
	}
	@ResponseBody
	@RequestMapping("/test")
	public String test(String userName) {
		log.info(userName);
		return userName;
	}
	@RequestMapping("/test1")
	public String test1() {
		
		return "associatedAccount";
	}
	@RequestMapping("/test2")
	public String test2() {
		
		return "index";
	}
	@RequestMapping("/test3")
	public String test3() {
		
		return "itemDesc";
	}
}
