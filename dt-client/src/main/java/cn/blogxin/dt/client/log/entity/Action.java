package cn.blogxin.dt.client.log.entity;

import cn.blogxin.dt.client.log.enums.ActionStatus;
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
     * 主事务ID
     */
    private String xid;

    /**
     * 分支事务名称
     */
    private String name;

    /**
     * 分支事务状态
     *
     * @see ActionStatus
     */
    private int status;

    /**
     * 分支事务创建时间
     */
    private Date gmtCreate;

    /**
     * 分支事务更新时间
     */
    private Date gmtModified;

}
