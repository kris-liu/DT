package cn.blogxin.dt.client.log.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kris
 */
@Data
public class Activity implements Serializable {

    private static final long serialVersionUID = -158716849842073007L;

    private long id;

    /**
     * 事务ID
     */
    private String xid;

    /**
     * 事务发起者名称
     */
    private String name;

    /**
     * 事务状态
     * @see ActivityStatus
     */
    private int status;

    /**
     * 事务超时时间
     */
    private Date timeOutTime;

    /**
     * 执行时间，每次重试后将执行时间向后延迟
     */
    private Date executionTime;

    /**
     * 重试次数
     */
    private int retryCount;

    /**
     * 事务创建时间
     */
    private Date gmtCreate;

    /**
     * 事务更新时间
     */
    private Date gmtModified;

}
