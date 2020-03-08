package cn.blogxin.pay.entity;

import lombok.Data;

import java.util.List;

/**
 * @author kris
 */
@Data
public class PayRequest {

    private String uid;

    private String orderId;

    private long amount;

    private List<PayChannelRequest> channels;

}
