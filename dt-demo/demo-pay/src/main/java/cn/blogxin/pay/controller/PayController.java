package cn.blogxin.pay.controller;

import cn.blogxin.pay.entity.PayChannel;
import cn.blogxin.pay.entity.PayChannelRequest;
import cn.blogxin.pay.entity.PayOrder;
import cn.blogxin.pay.entity.PayRequest;
import cn.blogxin.pay.enums.PayStatus;
import cn.blogxin.pay.service.PayService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kris
 */
@Controller
@Slf4j
public class PayController {

    @Resource
    private PayService payService;

    @RequestMapping(path = "/pay", method = RequestMethod.POST)
    @ResponseBody
    public Object pay(@RequestBody PayRequest payRequest) {
        try {
            PayOrder order = build(payRequest);
            List<PayChannel> channels = buildChannels(payRequest);
            return payService.accountAndCouponPay(order, channels);
        } catch (Exception e) {
            log.error("accountAndCouponPay error", e);
            return false;
        }
    }

    private PayOrder build(PayRequest payRequest) {
        PayOrder order = new PayOrder();
        order.setUid(payRequest.getUid());
        order.setOrderId(payRequest.getOrderId());
        order.setAmount(payRequest.getAmount());
        order.setStatus(PayStatus.SUCCESS.value());
        return order;
    }

    private List<PayChannel> buildChannels(PayRequest payRequest) {
        List<PayChannel> channels = Lists.newArrayList();
        for (PayChannelRequest request : payRequest.getChannels()) {
            PayChannel channel = new PayChannel();
            channel.setChannelId(request.getChannelId());
            channel.setAmount(request.getAmount());
            channel.setAssetId(StringUtils.defaultString(request.getAssetId()));
            channel.setUid(payRequest.getUid());
            channel.setOrderId(payRequest.getOrderId());
            channel.setStatus(PayStatus.SUCCESS.value());
            channels.add(channel);
        }
        return channels;
    }

}
