package cn.blogxin.coupon.service;

import cn.blogxin.common.coupon.dto.CouponDTO;

/**
 * @author kris
 */
public interface CouponService {

    boolean freeze(CouponDTO couponDTO);

    boolean commit(CouponDTO couponDTO);

    boolean unfreeze(CouponDTO couponDTO);

}
