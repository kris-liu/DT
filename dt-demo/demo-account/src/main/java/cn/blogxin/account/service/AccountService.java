package cn.blogxin.account.service;

import cn.blogxin.common.dto.AccountDTO;

/**
 * @author kris
 */
public interface AccountService {

    boolean freeze(AccountDTO accountDTO);

    boolean commit();

    boolean unfreeze();

}
