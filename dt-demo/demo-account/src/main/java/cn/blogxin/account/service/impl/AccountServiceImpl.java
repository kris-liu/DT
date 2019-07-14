package cn.blogxin.account.service.impl;

import cn.blogxin.account.mapper.AccountFlowMapper;
import cn.blogxin.account.mapper.AccountMapper;
import cn.blogxin.account.service.AccountService;
import cn.blogxin.common.account.dto.AccountDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private AccountFlowMapper accountFlowMapper;

    @Override
    public boolean transferIn(AccountDTO accountDTO) {
        return false;
    }

    @Override
    public boolean transferInCommit() {
        return false;
    }

    @Override
    public boolean transferInRollBack() {
        return false;
    }

    @Override
    public boolean transferOut(AccountDTO accountDTO) {
        return false;
    }

    @Override
    public boolean transferOutCommit() {
        return false;
    }

    @Override
    public boolean transferOutRollBack() {
        return false;
    }
}
