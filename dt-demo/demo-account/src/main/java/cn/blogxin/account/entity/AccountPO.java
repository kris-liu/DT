package cn.blogxin.account.entity;

import lombok.Data;

/**
 * @author kris
 */
@Data
public class AccountPO {

    private String uid;

    private long availableAmount;

    private long freezeAmount;

}
