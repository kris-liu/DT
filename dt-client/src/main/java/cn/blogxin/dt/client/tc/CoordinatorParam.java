package cn.blogxin.dt.client.tc;

import lombok.Data;

/**
 * 协调器参数
 *
 * @author kris
 */
@Data
public class CoordinatorParam {

    /**
     * 任务分片key，分库分表场景使用
     */
    private String shardingKey;

    /**
     * 任务单个分片单次捞取任务数量
     */
    private int limit = 20;

}
