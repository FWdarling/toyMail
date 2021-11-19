package com.fourwood.toymall.product.dao;

import com.fourwood.toymall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author fourwood
 * @email mlj2225042023@gmail.com
 * @date 2021-11-19 17:30:56
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
