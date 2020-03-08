package cn.blogxin.coupon.service.impl;

import cn.blogxin.common.coupon.dto.CouponDTO;
import cn.blogxin.coupon.entity.Coupon;
import cn.blogxin.coupon.enums.CouponStatus;
import cn.blogxin.coupon.mapper.CouponMapper;
import cn.blogxin.coupon.service.CouponService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponMapper couponMapper;

    @Override
    public boolean freeze(CouponDTO couponDTO) {
        Preconditions.checkArgument(couponMapper.freeze(couponDTO.getUid(), couponDTO.getCouponId(), CouponStatus.INIT.value(), CouponStatus.FREEZE.value(), couponDTO.getOrderId()) == 1, "冻结失败");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean commit(CouponDTO couponDTO) {
        Coupon coupon = couponMapper.queryForUpdate(couponDTO.getUid(), couponDTO.getCouponId());
        if (coupon.getStatus() == CouponStatus.COMMIT.value() && coupon.getOrderId().equals(couponDTO.getOrderId())) {
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
            return true;
        }
        if (!StringUtils.equals(coupon.getOrderId(), couponDTO.getOrderId())) {
            return true;
        }
        Preconditions.checkArgument(couponMapper.unfreeze(couponDTO.getUid(), couponDTO.getCouponId(), CouponStatus.FREEZE.value(), CouponStatus.INIT.value(), couponDTO.getOrderId()) == 1, "冻结失败");
        return true;
    }
}
