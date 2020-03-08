package cn.blogxin.common.account.service;

import cn.blogxin.common.account.dto.AccountDTO;
import cn.blogxin.dt.client.annotation.TwoPhaseCommit;

/**
 * @author kris
 */
public interface AccountDubboService {

    @TwoPhaseCommit(name = "transferOutAccount", confirmMethod = "commit", cancelMethod = "unfreeze")
    boolean freeze(AccountDTO accountDTO);

    void commit(AccountDTO accountDTO);

    void unfreeze(AccountDTO accountDTO);

}
