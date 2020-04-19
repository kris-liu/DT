package cn.blogxin.coupon.dubbo;

import cn.blogxin.common.coupon.dto.CouponDTO;
import cn.blogxin.common.coupon.service.CouponDubboService;
import cn.blogxin.coupon.service.CouponService;
import cn.blogxin.dt.client.context.DTParam;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Service
public class CouponDubboServiceImpl implements CouponDubboService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponDubboServiceImpl.class);

    @Resource
    private CouponService couponService;

    @Override
    public boolean freeze(CouponDTO couponDTO) {
        try {
            return couponService.freeze(couponDTO);
        } catch (Exception e) {
            LOGGER.error("freeze error", e);
            return false;
        }
    }

    @Override
    public void commit(DTParam dtParam, CouponDTO couponDTO) {
        couponService.commit(couponDTO);
    }

    @Override
    public void unfreeze(DTParam dtParam, CouponDTO couponDTO) {
        couponService.unfreeze(couponDTO);
    }

}
