package cn.blogxin.account.dubbo;

import cn.blogxin.account.service.AccountService;
import cn.blogxin.common.account.dto.AccountDTO;
import cn.blogxin.common.account.service.AccountDubboService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Service
public class AccountDubboServiceImpl implements AccountDubboService {

    @Resource
    private AccountService accountService;


    @Override
    public boolean freeze(AccountDTO accountDTO) {
        try {
            return accountService.freeze(accountDTO);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void commit(AccountDTO accountDTO) {
        accountService.commit(accountDTO);

    }

    @Override
    public void unfreeze(AccountDTO accountDTO) {
        accountService.unfreeze(accountDTO);
    }
}
