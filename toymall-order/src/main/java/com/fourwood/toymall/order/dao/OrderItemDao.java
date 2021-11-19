package com.fourwood.toymall.order.dao;

import com.fourwood.toymall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author fourwood
 * @email mlj2225042023@gmail.com
 * @date 2021-11-19 19:30:53
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
