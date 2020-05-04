package cn.blogxin.coupon.service.impl;

import cn.blogxin.common.coupon.dto.CouponDTO;
import cn.blogxin.coupon.entity.Coupon;
import cn.blogxin.coupon.enums.CouponStatus;
import cn.blogxin.coupon.mapper.CouponMapper;
import cn.blogxin.coupon.service.CouponService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Service
@Slf4j
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponMapper couponMapper;

    @Override
    public boolean freeze(CouponDTO couponDTO) {
        Coupon coupon = couponMapper.queryForUpdate(couponDTO.getUid(), couponDTO.getCouponId());
        if (coupon.getAmount() != couponDTO.getAmount()) {
            log.error("券抵扣金额不一致");
            return false;
        }
        return couponMapper.freeze(coupon.getUid(), coupon.getCouponId(), CouponStatus.INIT.value(), CouponStatus.FREEZE.value(), couponDTO.getOrderId()) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean commit(CouponDTO couponDTO) {
        Coupon coupon = couponMapper.queryForUpdate(couponDTO.getUid(), couponDTO.getCouponId());
        if (coupon.getStatus() == CouponStatus.COMMIT.value() && coupon.getOrderId().equals(couponDTO.getOrderId())) {
            log.info("幂等，已经提交成功");
            return true;
        }
        Preconditions.checkArgument(couponMapper.commit(couponDTO.getUid(), couponDTO.getCouponId(), CouponStatus.FREEZE.value(), CouponStatus.COMMIT.value(), couponDTO.getOrderId()) == 1, "冻结失败");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unfreeze(CouponDTO couponDTO) {
        Coupon coupon = couponMapper.queryForUpdate(couponDTO.getUid(), couponDTO.getCouponId());
        if (coupon.getStatus() == CouponStatus.INIT.value() && coupon.getOrderId().equals(couponDTO.getOrderId())) {
            log.info("幂等，已经解冻成功");
            return true;
        }
        if (!StringUtils.equals(coupon.getOrderId(), couponDTO.getOrderId())) {
            log.info("当前券未绑定在该订单上。有可能是未发起冻结成功，解冻时直接返回解冻成功，允许空回滚；也有可能是解冻成功后又被其他订单冻结，返回解冻成功，保证幂等");
            return true;
        }
        Preconditions.checkArgument(couponMapper.unfreeze(couponDTO.getUid(), couponDTO.getCouponId(), CouponStatus.FREEZE.value(), CouponStatus.INIT.value(), couponDTO.getOrderId()) == 1, "解冻失败");
        return true;
    }
}
