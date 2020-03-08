package cn.blogxin.pay.entity;

import lombok.Data;

/**
 * @author kris
 */
@Data
public class PayOrder {

    private long id;

    private String uid;

    private String orderId;

    private int status;

    private long amount;

}
