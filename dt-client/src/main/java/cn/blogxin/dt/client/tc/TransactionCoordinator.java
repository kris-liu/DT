package cn.blogxin.dt.client.tc;

/**
 * 事务协调器。
 * 维护分布式事务的状态，负责对分布式事务进行补偿提交或回滚。
 *
 * @author kris
 */
public interface TransactionCoordinator {

    /**
     * 对二阶段提交失败的事务进行批量补偿
     *
     * @param param CoordinatorParam
     */
    void batchReTry(CoordinatorParam param);

}
