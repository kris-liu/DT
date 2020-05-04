package cn.blogxin.pay.service;

import cn.blogxin.pay.entity.PayChannel;
import cn.blogxin.pay.entity.PayOrder;

import java.util.List;

/**
 * @author kris
 */
public interface PayService {

    /**
     * 余额加券支付
     *
     * @param payOrder
     * @param channels
     * @return
     */
    boolean accountAndCouponPay(PayOrder payOrder, List<PayChannel> channels);

}
