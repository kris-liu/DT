package cn.blogxin.common.account.service;

import cn.blogxin.common.account.dto.AccountDTO;

/**
 * @author kris
 */
public interface AccountDubboService {

    boolean freeze(AccountDTO accountDTO);

    void commit(AccountDTO accountDTO);

    void unfreeze(AccountDTO accountDTO);

}
