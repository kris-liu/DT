package cn.blogxin.common.account.service;

import cn.blogxin.common.account.dto.AccountDTO;
import cn.blogxin.dt.client.annotation.TwoPhaseCommit;
import cn.blogxin.dt.client.context.DTParam;

/**
 * @author kris
 */
public interface AccountDubboService {

    @TwoPhaseCommit(name = "transferOutAccount", confirmMethod = "commit", cancelMethod = "unfreeze")
    boolean freeze(AccountDTO accountDTO);

    void commit(DTParam dtParam, AccountDTO accountDTO);

    void unfreeze(DTParam dtParam, AccountDTO accountDTO);

}
