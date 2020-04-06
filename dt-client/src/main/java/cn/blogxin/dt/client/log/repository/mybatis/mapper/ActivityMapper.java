package cn.blogxin.dt.client.log.repository.mybatis.mapper;

import cn.blogxin.dt.client.log.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
     *
     * @param xid
     * @return
     */
    Activity query(@Param("xid") String xid);

    /**
     * 更新主事务记录状态
     * @param xid
     * @param fromStatus
     * @param toStatus
     * @return
     */
    int updateStatus(@Param("xid") String xid, @Param("fromStatus") int fromStatus, @Param("toStatus") int toStatus);

}
