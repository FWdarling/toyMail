package com.fourwood.toymall.coupon.dao;

import com.fourwood.toymall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author fourwood
 * @email mlj2225042023@gmail.com
 * @date 2021-11-19 19:11:07
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
