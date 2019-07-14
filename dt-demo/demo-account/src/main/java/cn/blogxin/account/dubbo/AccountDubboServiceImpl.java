package cn.blogxin.account.dubbo;

import cn.blogxin.account.service.AccountService;
import cn.blogxin.common.account.dto.AccountDTO;
import cn.blogxin.common.account.service.AccountDubboService;
import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Service
public class AccountDubboServiceImpl implements AccountDubboService {

    @Resource
    private AccountService accountService;

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
