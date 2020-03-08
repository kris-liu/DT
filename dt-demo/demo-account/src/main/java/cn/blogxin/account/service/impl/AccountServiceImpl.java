package cn.blogxin.account.service.impl;

import cn.blogxin.account.entity.AccountFlow;
import cn.blogxin.account.enums.AccountFlowStatus;
import cn.blogxin.account.mapper.AccountFlowMapper;
import cn.blogxin.account.mapper.AccountMapper;
import cn.blogxin.account.service.AccountService;
import cn.blogxin.common.account.dto.AccountDTO;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private AccountFlowMapper accountFlowMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean freeze(AccountDTO accountDTO) {
        AccountFlow accountFlow = initFlow(accountDTO);
        accountFlowMapper.insert(accountFlow);
        Preconditions.checkArgument(accountMapper.freeze(accountFlow.getUid(), accountFlow.getAmount()) == 1, "冻结金额失败");
        return true;
    }

    private AccountFlow initFlow(AccountDTO accountDTO) {
        AccountFlow flow = new AccountFlow();
        flow.setUid(accountDTO.getUid());
        flow.setOrderId(accountDTO.getOrderId());
        flow.setAmount(accountDTO.getAmount());
        flow.setStatus(AccountFlowStatus.FREEZE.value());
        return flow;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean commit(AccountDTO accountDTO) {
        AccountFlow flow = accountFlowMapper.queryForUpdate(accountDTO.getUid(), accountDTO.getOrderId());
        if (flow.getStatus() == AccountFlowStatus.COMMIT.value()) {
            LOGGER.info("幂等，已经提交成功");
            return true;
        }
        Preconditions.checkArgument(accountFlowMapper.updateStatus(flow.getUid(), flow.getOrderId(), AccountFlowStatus.FREEZE.value(), AccountFlowStatus.COMMIT.value()) == 1, "提交流水失败");
        Preconditions.checkArgument(accountMapper.commit(flow.getUid(), flow.getAmount()) == 1, "提交冻结金额失败");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unfreeze(AccountDTO accountDTO) {
        AccountFlow flow = accountFlowMapper.queryForUpdate(accountDTO.getUid(), accountDTO.getOrderId());
        if (flow == null) {
            LOGGER.info("未发起冻结成功，解冻时直接返回解冻成功，允许空回滚");
            return true;
        }
        if (flow.getStatus() == AccountFlowStatus.UNFREEZE.value()) {
            LOGGER.info("幂等，已经解冻成功");
            return true;
        }
        Preconditions.checkArgument(accountFlowMapper.updateStatus(flow.getUid(), flow.getOrderId(), AccountFlowStatus.FREEZE.value(), AccountFlowStatus.UNFREEZE.value()) == 1, "解冻流水失败");
        Preconditions.checkArgument(accountMapper.unfreeze(flow.getUid(), flow.getAmount()) == 1, "解冻冻结金额失败");
        return true;
    }
}
