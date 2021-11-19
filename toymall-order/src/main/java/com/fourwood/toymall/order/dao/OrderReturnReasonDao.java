package com.fourwood.toymall.order.dao;

import com.fourwood.toymall.order.entity.OrderReturnReasonEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退货原因
 * 
 * @author fourwood
 * @email mlj2225042023@gmail.com
 * @date 2021-11-19 19:30:53
 */
@Mapper
public interface OrderReturnReasonDao extends BaseMapper<OrderReturnReasonEntity> {
	
}
