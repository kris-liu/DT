package cn.blogxin.dt.client.log.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分支事务记录
 *
 * @author kris
 */
@Data
public class Action implements Serializable {

    private static final long serialVersionUID = -3797643582038854915L;

    private long id;

    /**
     * 事务ID
     */
    private String xid;

    /**
     * 事务名称
     */
    private String name;

    /**
     * 事务状态
     *
     * @see ActionStatus
     */
    private int status;

    /**
     * 事务创建时间
     */
    private Date gmtCreate;

    /**
     * 事务更新时间
     */
    private Date gmtModified;

}
