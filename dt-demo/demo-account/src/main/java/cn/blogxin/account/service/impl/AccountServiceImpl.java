package cn.blogxin.account.service.impl;

import cn.blogxin.account.service.AccountService;
import cn.blogxin.common.dto.AccountDTO;

/**
 * @author kris
 */
public class AccountServiceImpl implements AccountService {


    @Override
    public boolean freeze(AccountDTO accountDTO) {
        return false;
    }

    @Override
    public boolean commit() {
        return false;
    }

    @Override
    public boolean unfreeze() {
        return false;
    }
}
