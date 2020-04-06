package cn.blogxin.dt.client.log.repository.mybatis.mapper;

import cn.blogxin.dt.client.log.entity.Action;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kris
 */
@Mapper
@Repository
public interface ActionMapper {

    /**
     * 插入分支事务记录
     *
     * @param action
     * @return
     */
    int insert(Action action);

    /**
     * 根据事务ID查询分支事务记录列表
     *
     * @param xid
     * @return
     */
    List<Action> query(@Param("xid") String xid);

    /**
     * 根据事务ID和分支事务名更新分支事务记录状态
     *
     * @param xid
     * @param name
     * @param fromStatus
     * @param toStatus
     * @return
     */
    int updateStatus(@Param("xid") String xid, @Param("name") String name, @Param("fromStatus") int fromStatus, @Param("toStatus") int toStatus);

}
