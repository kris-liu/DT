package cn.blogxin.dt.client.rm;

import cn.blogxin.dt.client.aop.ActionResource;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 资源管理器。控制分支事务，负责分支注册、状态汇报，驱动分支事务的提交和回滚。
 *
 * @author kris
 */
public class ResourceManager {

    private static final Map<String, ActionResource> RESOURCES = Maps.newConcurrentMap();

    public static void registerResource(ActionResource resource) {
        RESOURCES.put(resource.getActionName(), resource);
    }




}
