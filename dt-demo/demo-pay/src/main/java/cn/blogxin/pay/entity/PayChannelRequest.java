package cn.blogxin.pay.entity;

import lombok.Data;

/**
 * @author kris
 */
@Data
public class PayChannelRequest {

    private int channelId;

    private long amount;

    private String assetId;

}
