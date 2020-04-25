package cn.blogxin.dt.client.log.repository;

import cn.blogxin.dt.client.log.entity.Activity;
import cn.blogxin.dt.client.log.enums.ActivityStatus;

/**
 * @author kris
 */
public interface ActivityRepository {

    /**
     * 插入主事务记录
     * 需要挂起业务事务，启用一个新事务来独立执行。
     *
     * @param activity Activity
     * @see org.springframework.transaction.annotation.Propagation.REQUIRES_NEW
     */
    void insert(Activity activity);

    /**
     * 根据事务ID查询主事务记录
     *
     * @param xid 事务ID
     * @return Activity
     */
    Activity query(String xid);

    /**
     * 更新主事务记录状态
     * INIT状态更新为COMMIT状态时，需要直接使用业务事务执行，业务事务执行成功则执行成功，失败则回滚
     *
     * @param xid        事务ID
     * @param fromStatus 原状态
     * @param toStatus   新状态
     * @see org.springframework.transaction.annotation.Propagation.REQUIRED
     */
    void updateStatus(String xid, ActivityStatus fromStatus, ActivityStatus toStatus);

}
