package cn.blogxin.coupon.dubbo;

import cn.blogxin.common.coupon.dto.CouponDTO;
import cn.blogxin.common.coupon.service.CouponDubboService;
import cn.blogxin.coupon.service.CouponService;
import cn.blogxin.dt.api.context.DTParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Service
@Slf4j
public class CouponDubboServiceImpl implements CouponDubboService {

    @Resource
    private CouponService couponService;

    @Override
    public boolean freeze(CouponDTO couponDTO) {
        log.info("access CouponDubboService freeze couponDTO:{}", couponDTO);
        try {
            return couponService.freeze(couponDTO);
        } catch (Exception e) {
            log.error("freeze error", e);
            return false;
        }
    }

    @Override
    public void commit(DTParam dtParam, CouponDTO couponDTO) {
        log.info("access CouponDubboService commit dtParam:{}, couponDTO:{}", dtParam, couponDTO);
        couponService.commit(couponDTO);
    }

    @Override
    public void unfreeze(DTParam dtParam, CouponDTO couponDTO) {
        log.info("access CouponDubboService unfreeze dtParam:{}, couponDTO:{}", dtParam, couponDTO);
        couponService.unfreeze(couponDTO);
    }

}
