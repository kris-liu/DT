package cn.blogxin.dt.client.log.repository;

import cn.blogxin.dt.client.exception.DTException;
import cn.blogxin.dt.client.log.entity.Activity;
import cn.blogxin.dt.client.log.repository.mybatis.mapper.ActivityMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Repository
public class ActivityRepository {

    @Resource
    private ActivityMapper activityMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insert(Activity activity) {
        int insert = activityMapper.insert(activity);
        if (insert != NumberUtils.INTEGER_ONE) {
            throw new DTException("插入主事务记录Activity异常，事务回滚");
        }
    }

    public Activity query(String xid) {
        return activityMapper.query(xid);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateStatus(String xid, int fromStatus, int toStatus) {
        int update = activityMapper.updateStatus(xid, fromStatus, toStatus);
        if (update != NumberUtils.INTEGER_ONE) {
            throw new DTException("更新主事务记录Activity异常，事务回滚");
        }
    }

}