package cn.blogxin.pay.entity;

import lombok.Data;

/**
 * @author kris
 */
@Data
public class PayChannel {

    private long id;

    private String uid;

    private String orderId;

    private int channelId;

    private int status;

    private long amount;

    private String assetId;

}
