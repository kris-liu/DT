package cn.blogxin.common.coupon.dto;

import lombok.Data;

/**
 * @author kris
 */
@Data
public class CouponDTO {

    private String uid;

    private String orderId;

    private String couponId;

    private long amount;

}
