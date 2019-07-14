package cn.blogxin.account.mapper;

import cn.blogxin.account.entity.AccountPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author kris
 */
@Mapper
public interface AccountMapper {

    boolean freeze(AccountPO accountPO);

    boolean commit();

    boolean unfreeze();

}
