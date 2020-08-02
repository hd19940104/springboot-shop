package com.zixue.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zixue.base.controller.BaseController;
import com.zixue.feign.ItemDescFeign;
import com.zixue.feign.ItemFeign;
import com.zixue.utils.ResultUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 商品详情
 * 
 * @author Administrator
 *
 */
@Slf4j
@Controller
public class ItemDescController extends BaseController {

	private static final String ITEMDESC = "itemDesc";
	public static final String ERROR = "common/error";
	@Autowired
	private ItemDescFeign itemDescFeign;
	@Autowired
	private ItemFeign itemFeign;

	/**
	 * 课程详情
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/itemDesc")
	public String itemDesc(HttpServletRequest request, Long id) {
		try {
			Map<String, Object> resultItem = itemFeign.geItem(id);
			Map<String, Object> item = (Map<String, Object>) ResultUtils.getResultMap(resultItem);
			request.setAttribute("item", item);
			Map<String, Object> reusltItemDesc = itemDescFeign.getItemDesc(id);
			Map<String, Object> itemDesc = (Map<String, Object>) ResultUtils.getResultMap(reusltItemDesc);
			request.setAttribute("itemDesc", itemDesc);
			return ITEMDESC;
		} catch (Exception e) {
			log.error("###itemDesc() ERROR:", e);
			return ERROR;
		}

	}

}
