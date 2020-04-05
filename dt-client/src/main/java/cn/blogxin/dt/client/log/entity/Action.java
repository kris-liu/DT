package cn.blogxin.dt.client.log.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kris
 */
@Data
public class Action implements Serializable {

    private static final long serialVersionUID = -3797643582038854915L;

    private long id;

    private String xid;

    private String name;

    /**
     * 事务状态
     * @see ActionStatus
     */
    private int status;

    private Date gmtCreate;

    private Date gmtModified;

}
