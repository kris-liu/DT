package cn.blogxin.coupon.entity;

import lombok.Data;

/**
 * @author kris
 */
@Data
public class Coupon {

    private long id;

    private String uid;

    private String couponId;

    private int status;

    private String orderId;

}
