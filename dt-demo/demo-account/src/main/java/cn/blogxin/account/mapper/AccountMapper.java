package cn.blogxin.account.mapper;

import cn.blogxin.account.entity.AccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author kris
 */
@Mapper
@Repository
public interface AccountMapper {

    AccountPO query(String uid);

    boolean freeze(AccountPO accountPO);

    boolean commit();

    boolean unfreeze();

}
