package cn.blogxin.pay.service.impl;

import cn.blogxin.common.account.dto.AccountDTO;
import cn.blogxin.common.account.service.AccountDubboService;
import cn.blogxin.common.coupon.dto.CouponDTO;
import cn.blogxin.common.coupon.service.CouponDubboService;
import cn.blogxin.dt.client.log.repository.ActionRepository;
import cn.blogxin.dt.client.log.repository.ActivityRepository;
import cn.blogxin.dt.client.tm.TransactionManager;
import cn.blogxin.pay.entity.PayChannel;
import cn.blogxin.pay.entity.PayOrder;
import cn.blogxin.pay.enums.PayChannelEnum;
import cn.blogxin.pay.mapper.PayChannelMapper;
import cn.blogxin.pay.mapper.PayOrderMapper;
import cn.blogxin.pay.service.PayService;
import com.google.common.base.Preconditions;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kris
 */
@Service
public class PayServiceImpl implements PayService {


    private AccountDubboService accountDubboService;

    @Reference
    public void setAccountDubboService(AccountDubboService accountDubboService) {
        this.accountDubboService = accountDubboService;
    }

    @Reference
    private CouponDubboService couponDubboService;

    @Resource
    private PayOrderMapper payOrderMapper;

    @Resource
    private PayChannelMapper payChannelMapper;

    @Resource
    private TransactionManager dtTransactionManager;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean accountAndCouponPay(PayOrder payOrder, List<PayChannel> channels) {
        AccountDTO accountDTO = buildAccountDTO(payOrder, channels);
        CouponDTO couponDTO = buildCouponDTO(payOrder, channels);
        try {
            dtTransactionManager.start();
            payOrderMapper.insert(payOrder);
            payChannelMapper.insert(channels);

            Preconditions.checkArgument(accountDubboService.freeze(accountDTO), "余额冻结失败");

            Preconditions.checkArgument(couponDubboService.freeze(couponDTO), "券冻结失败");

//            int i = 10/0;

            accountDubboService.commit(accountDTO);
            couponDubboService.commit(couponDTO);
        } catch (Exception e) {
            accountDubboService.unfreeze(accountDTO);
            couponDubboService.unfreeze(couponDTO);
        }

        return true;
    }


    private AccountDTO buildAccountDTO(PayOrder payOrder, List<PayChannel> channels) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUid(payOrder.getUid());
        accountDTO.setOrderId(payOrder.getOrderId());
        for (PayChannel channel : channels) {
            if (channel.getChannelId() == PayChannelEnum.ACCOUNT.value()) {
                accountDTO.setAmount(channel.getAmount());
            }
        }
        return accountDTO;
    }

    private CouponDTO buildCouponDTO(PayOrder payOrder, List<PayChannel> channels) {
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setUid(payOrder.getUid());
        couponDTO.setOrderId(payOrder.getOrderId());
        for (PayChannel channel : channels) {
            if (channel.getChannelId() == PayChannelEnum.COUPON.value()) {
                couponDTO.setAmount(channel.getAmount());
                couponDTO.setCouponId(channel.getAssetId());
            }
        }
        return couponDTO;
    }
}
