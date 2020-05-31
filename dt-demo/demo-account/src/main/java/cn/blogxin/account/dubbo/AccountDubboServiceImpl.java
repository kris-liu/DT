package cn.blogxin.account.dubbo;

import cn.blogxin.account.service.AccountService;
import cn.blogxin.common.account.dto.AccountDTO;
import cn.blogxin.common.account.service.AccountDubboService;
import cn.blogxin.dt.api.context.DTParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Service
@Slf4j
public class AccountDubboServiceImpl implements AccountDubboService {

    @Resource
    private AccountService accountService;


    @Override
    public boolean freeze(AccountDTO accountDTO) {
        log.info("access AccountDubboService freeze accountDTO:{}", accountDTO);
        try {
            return accountService.freeze(accountDTO);
        } catch (Exception e) {
            log.error("freeze error", e);
            return false;
        }
    }

    @Override
    public void commit(DTParam dtParam, AccountDTO accountDTO) {
        log.info("access AccountDubboService commit dtParam:{}, accountDTO:{}", dtParam, accountDTO);
        accountService.commit(accountDTO);

    }

    @Override
    public void unfreeze(DTParam dtParam, AccountDTO accountDTO) {
        log.info("access AccountDubboService unfreeze dtParam:{}, accountDTO:{}", dtParam, accountDTO);
        accountService.unfreeze(accountDTO);
    }
}
