package com.fourwood.toymall.ware.dao;

import com.fourwood.toymall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author fourwood
 * @email mlj2225042023@gmail.com
 * @date 2021-11-19 19:33:30
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
