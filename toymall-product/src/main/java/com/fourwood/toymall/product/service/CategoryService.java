package com.fourwood.toymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fourwood.common.utils.PageUtils;
import com.fourwood.toymall.product.dto.CategoryListTreeDto;
import com.fourwood.toymall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author fourwood
 * @email mlj2225042023@gmail.com
 * @date 2021-11-19 17:30:56
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryListTreeDto> listWithTree();

    void removeMenuByIds(List<Long> catIds);
}

