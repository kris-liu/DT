package cn.blogxin.common.coupon.service;

import cn.blogxin.common.coupon.dto.CouponDTO;
import cn.blogxin.dt.api.annotation.TwoPhaseCommit;
import cn.blogxin.dt.api.context.DTParam;

/**
 * @author kris
 */
public interface CouponDubboService {

    @TwoPhaseCommit(name = "useCoupon", confirmMethod = "commit", cancelMethod = "unfreeze")
    boolean freeze(CouponDTO couponDTO);

    void commit(DTParam dtParam, CouponDTO couponDTO);

    void unfreeze(DTParam dtParam, CouponDTO couponDTO);

}
