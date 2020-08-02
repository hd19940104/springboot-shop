package com.zixue.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zixue.common.api.BaseApiService;
import com.zixue.dao.ItemDescDao;
import com.zixue.entity.ItemDescEntity;
import com.zixue.service.ItemDescService;



@RestController
public class ItemDescServiceImpl extends BaseApiService implements ItemDescService {
	@Autowired
	private ItemDescDao itemDescDao;

	@RequestMapping("/getItemDesc")
	public Map<String, Object> getItemDesc(@RequestParam("id") Long id) {
		ItemDescEntity itemDesc = itemDescDao.getItemDesc(id);
		return setResutSuccessData(itemDesc);
	}

}
