package cn.blogxin.coupon.dubbo;

import cn.blogxin.common.coupon.dto.CouponDTO;
import cn.blogxin.common.coupon.service.CouponDubboService;
import cn.blogxin.coupon.service.CouponService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Service
public class CouponDubboServiceImpl implements CouponDubboService {

    @Resource
    private CouponService couponService;

    @Override
    public boolean freeze(CouponDTO couponDTO) {
        try {
            return couponService.freeze(couponDTO);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void commit(CouponDTO couponDTO) {
        couponService.commit(couponDTO);
    }

    @Override
    public void unfreeze(CouponDTO couponDTO) {
        couponService.unfreeze(couponDTO);
    }
}
