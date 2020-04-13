package cn.blogxin.dt.client.context;

import cn.blogxin.dt.client.log.entity.Activity;

import java.util.Date;

/**
 * @author kris
 */
public enum DTContextEnum {

    /**
     * 事务ID
     */
    XID("xid", String.class),

    /**
     * 事务开始时间
     */
    START_TIME("startTime", Date.class),

    /**
     * 事务超时时间
     */
    TIMEOUT_TIME("timeoutTime", Date.class),

    /**
     * 主事务记录
     */
    ACTIVITY("activity", Activity.class),

    /**
     * 分支事务记录
     */
    ACTION_CONTEXT("actionContext", ActionContext.class);

    private String key;

    private Class keyClass;

    DTContextEnum(String key, Class keyClass) {
        this.key = key;
        this.keyClass = keyClass;
    }

    public String getKey() {
        return this.key;
    }

    public Class getKeyClass() {
        return keyClass;
    }

}
