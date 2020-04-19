package cn.blogxin.account.dubbo;

import cn.blogxin.account.service.AccountService;
import cn.blogxin.common.account.dto.AccountDTO;
import cn.blogxin.common.account.service.AccountDubboService;
import cn.blogxin.dt.client.context.DTParam;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Service
public class AccountDubboServiceImpl implements AccountDubboService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDubboServiceImpl.class);

    @Resource
    private AccountService accountService;


    @Override
    public boolean freeze(AccountDTO accountDTO) {
        try {
            return accountService.freeze(accountDTO);
        } catch (Exception e) {
            LOGGER.error("freeze error", e);
            return false;
        }
    }

    @Override
    public void commit(DTParam dtParam, AccountDTO accountDTO) {
        accountService.commit(accountDTO);

    }

    @Override
    public void unfreeze(DTParam dtParam, AccountDTO accountDTO) {
        accountService.unfreeze(accountDTO);
    }
}
