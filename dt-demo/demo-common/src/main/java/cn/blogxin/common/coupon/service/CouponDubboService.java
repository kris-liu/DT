package cn.blogxin.common.coupon.service;

import cn.blogxin.common.coupon.dto.CouponDTO;
import cn.blogxin.dt.client.annotation.TwoPhaseCommit;

/**
 * @author kris
 */
public interface CouponDubboService {

    @TwoPhaseCommit(name = "useCoupon", confirmMethod = "commit", cancelMethod = "unfreeze")
    boolean freeze(CouponDTO couponDTO);

    void commit(CouponDTO couponDTO);

    void unfreeze(CouponDTO couponDTO);

}
