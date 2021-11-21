package com.fourwood.toymall.member.feign;

import com.fourwood.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * description: CouponFeignService
 * date: 11/22/21 1:33 AM
 * author: fourwood
 */

@FeignClient("toymall-coupon")
public interface CouponFeignService {

    @RequestMapping("/coupon/coupon/member/list")
    public R memberCoupons();
}
