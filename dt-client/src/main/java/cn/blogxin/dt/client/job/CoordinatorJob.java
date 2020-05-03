package cn.blogxin.dt.client.job;

import cn.blogxin.dt.client.spring.DistributedTransactionProperties;
import cn.blogxin.dt.client.tc.CoordinatorParam;
import cn.blogxin.dt.client.tc.TransactionCoordinator;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import javax.annotation.Resource;

/**
 * 分布式事务协调器任务，负责任务分片和调度
 *
 * @author kris
 */
public class CoordinatorJob implements SimpleJob {

    @Resource
    private TransactionCoordinator transactionCoordinator;

    @Resource
    private DistributedTransactionProperties distributedTransactionProperties;

    @Override
    public void execute(ShardingContext shardingContext) {
        CoordinatorParam param = new CoordinatorParam();
        param.setShardingKey(String.valueOf(shardingContext.getShardingItem()));
        param.setLimit(distributedTransactionProperties.getJob().getLimit());
        transactionCoordinator.batchReTry(param);
    }

}
