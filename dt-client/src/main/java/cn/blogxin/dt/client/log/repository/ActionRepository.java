package cn.blogxin.dt.client.log.repository;

import cn.blogxin.dt.client.log.entity.Action;
import cn.blogxin.dt.client.log.enums.ActionStatus;

import java.util.List;

/**
 * 分支事务记录操作
 *
 * @author kris
 */
public interface ActionRepository {

    /**
     * 插入分支事务记录
     * 需要挂起业务事务，启用一个新事务来独立执行。
     *
     * @param action Action
     * @see org.springframework.transaction.annotation.Propagation.REQUIRES_NEW
     */
    void insert(Action action);

    /**
     * 根据事务ID查询分支事务记录列表
     *
     * @param xid 事务ID
     * @return Action
     */
    List<Action> query(String xid);

    /**
     * 根据事务ID和分支事务名更新分支事务记录状态
     *
     * @param xid        事务ID
     * @param name       分支事务名称
     * @param fromStatus 原状态
     * @param toStatus   新状态
     */
    void updateStatus(String xid, String name, ActionStatus fromStatus, ActionStatus toStatus);

}
