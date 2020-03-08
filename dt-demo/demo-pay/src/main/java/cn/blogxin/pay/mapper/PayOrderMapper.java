package cn.blogxin.pay.mapper;

import cn.blogxin.pay.entity.PayOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author kris
 */
@Mapper
@Repository
public interface PayOrderMapper {

    PayOrder query(@Param("uid") String uid, @Param("orderId") String orderId);

    int insert(PayOrder order);

}
