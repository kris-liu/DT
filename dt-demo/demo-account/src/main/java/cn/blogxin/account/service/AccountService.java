package cn.blogxin.account.service;

import cn.blogxin.common.account.dto.AccountDTO;

/**
 * @author kris
 */
public interface AccountService {

    boolean freeze(AccountDTO accountDTO);

    boolean commit(AccountDTO accountDTO);

    boolean unfreeze(AccountDTO accountDTO);
}
