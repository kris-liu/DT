package cn.blogxin.common.account.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kris
 */
@Data
public class AccountDTO implements Serializable {

    private static final long serialVersionUID = 608984118113193623L;

    private String uid;

    private String orderId;

    private long amount;

}
