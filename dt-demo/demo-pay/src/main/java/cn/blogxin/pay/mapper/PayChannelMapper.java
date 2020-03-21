package cn.blogxin.pay.mapper;

import cn.blogxin.pay.entity.PayChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kris
 */
@Repository
public interface PayChannelMapper {

    List<PayChannel> query(@Param("uid") String uid, @Param("orderId") String orderId);

    int insert(@Param("channels") List<PayChannel> channels);

}
