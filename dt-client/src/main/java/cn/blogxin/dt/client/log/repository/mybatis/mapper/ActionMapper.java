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

    int insert(Action action);

    List<Action> query(@Param("xid") String xid);

}
