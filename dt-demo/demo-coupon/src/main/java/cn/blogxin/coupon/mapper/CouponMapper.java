package cn.blogxin.coupon.mapper;

import cn.blogxin.coupon.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author kris
 */
@Mapper
@Repository
public interface CouponMapper {

    void insert(Coupon coupon);

    Coupon queryForUpdate(@Param("uid") String uid, @Param("couponId") String couponId);

    int freeze(@Param("uid") String uid, @Param("couponId") String couponId, @Param("currentStatus") int currentStatus, @Param("status") int status, @Param("orderId") String orderId);

    int commit(@Param("uid") String uid, @Param("couponId") String couponId, @Param("currentStatus") int currentStatus, @Param("status") int status, @Param("orderId") String orderId);

    int unfreeze(@Param("uid") String uid, @Param("couponId") String couponId, @Param("currentStatus") int currentStatus, @Param("status") int status, @Param("orderId") String orderId);

}
