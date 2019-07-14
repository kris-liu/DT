package cn.blogxin.account.service;

import cn.blogxin.common.account.dto.AccountDTO;

/**
 * @author kris
 */
public interface AccountService {

    boolean transferIn(AccountDTO accountDTO);

    boolean transferInCommit();

    boolean transferInRollBack();

    boolean transferOut(AccountDTO accountDTO);

    boolean transferOutCommit();

    boolean transferOutRollBack();

}