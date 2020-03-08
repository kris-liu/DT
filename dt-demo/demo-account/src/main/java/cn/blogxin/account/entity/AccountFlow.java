package cn.blogxin.account.entity;

import lombok.Data;

/**
 * @author kris
 */
@Data
public class AccountFlow {

    private long id;

    private String uid;

    private String orderId;

    private int status;

    private long amount;

}
