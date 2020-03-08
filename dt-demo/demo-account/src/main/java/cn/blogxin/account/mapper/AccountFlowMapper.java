package cn.blogxin.account.mapper;

import cn.blogxin.account.entity.AccountFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author kris
 */
@Mapper
@Repository
public interface AccountFlowMapper {
    void insertFlow(AccountFlow accountFlow);

    AccountFlow queryForUpdate(@Param("uid") String uid, @Param("orderId") String orderId);

    int updateStatus(@Param("uid") String uid, @Param("orderId") String orderId, @Param("status") int status);

}
