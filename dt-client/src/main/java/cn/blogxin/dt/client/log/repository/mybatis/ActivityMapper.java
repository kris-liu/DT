package cn.blogxin.dt.client.log.repository.mybatis;

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

    int insert(Activity activity);

    Activity query(@Param("xid") String xid);

}
