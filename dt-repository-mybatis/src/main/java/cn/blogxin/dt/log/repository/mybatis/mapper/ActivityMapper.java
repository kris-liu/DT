package cn.blogxin.dt.log.repository.mybatis.mapper;

import cn.blogxin.dt.client.log.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author kris
 */
@Mapper
@Repository
public interface ActivityMapper {

    /**
     * 插入主事务记录
     *
     * @param activity
     * @return
     */
    int insert(Activity activity);

    /**
     * 根据事务ID查询主事务记录
     * 快照读
     *
     * @param xid
     * @return
     */
    Activity query(@Param("xid") String xid);

    /**
     * 根据事务ID查询主事务记录
     * 当前读
     *
     * @param xid
     * @return
     */
    Activity queryForUpdate(@Param("xid") String xid);

    /**
     * 更新主事务记录状态
     *
     * @param xid
     * @param fromStatus
     * @param toStatus
     * @return
     */
    int updateStatus(@Param("xid") String xid, @Param("fromStatus") int fromStatus, @Param("toStatus") int toStatus);

    /**
     * 查询未完成的事务记录列表
     *
     * @param shardingKey   分片key，分库分表场景使用
     * @param executionTime 执行时间
     * @param limit         分页数量
     * @return 未完成的事务记录列表
     */
    List<Activity> queryUnfinished(String shardingKey, @Param("executionTime") Date executionTime, @Param("limit") int limit);

    /**
     * 更新重试次数和重试执行时间
     *
     * @param xid         事务ID
     * @param fromStatus  原状态
     * @param retryCount  重试次数
     * @param executeTime 重试执行时间
     */
    void updateRetry(@Param("xid") String xid, @Param("fromStatus") int fromStatus, @Param("retryCount") int retryCount, @Param("executeTime") Date executeTime);

}
