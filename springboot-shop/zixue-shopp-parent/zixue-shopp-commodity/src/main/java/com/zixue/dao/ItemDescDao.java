package com.zixue.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import com.zixue.common.mybatis.BaseDao;
import com.zixue.entity.ItemDescEntity;


/**
 * 商品详情
 * @author houdo
 *
 */
@Mapper
public interface ItemDescDao extends BaseDao {
	@Select(" SELECT id AS id , itemDesc as itemDesc , created as created,updated as updated  FROM tb_item_desc  where id=#{id} ")
	public ItemDescEntity getItemDesc(@Param("id") Long id);
}
