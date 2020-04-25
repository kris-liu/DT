package cn.blogxin.dt.client.rm;

import cn.blogxin.dt.client.log.entity.Action;

/**
 * 资源管理器。
 * 控制分支事务，负责分支注册、状态汇报，驱动分支事务的提交和回滚。
 *
 * @author kris
 */
public interface ResourceManager {

    /**
     * 启动项目前，预先注册所有ActionResource资源
     *
     * @param resource ActionResource
     */
    void registerResource(ActionResource resource);

    /**
     * 注册当前分布式事务的Action
     *
     * @param action Action
     */
    void registerAction(Action action);

    /**
     * 提交当前分布式事务
     *
     * @return 返回true，返回false时需要由TC重试，重试失败需要人工干预
     */
    boolean commitAction();

    /**
     * 提交当前分布式事务
     *
     * @return 返回true，返回false时需要由TC重试，重试失败需要人工干预
     */
    boolean rollbackAction();

}
