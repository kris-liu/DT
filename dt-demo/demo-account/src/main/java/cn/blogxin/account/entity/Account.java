package cn.blogxin.account.entity;

import lombok.Data;

/**
 * @author kris
 */
@Data
public class Account {

    private long id;

    private String uid;

    private long availableAmount;

    private long freezeAmount;

}
