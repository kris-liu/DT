package cn.blogxin.dt.client.rm;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author kris
 */
@Data
public class ActionResource {

    /**
     * action名称
     *
     * @see cn.blogxin.dt.client.util.Utils.getActionName()
     */
    private String actionName;

    /**
     * action的Bean
     */
    private Object actionBean;

    /**
     * 一阶段方法
     */
    private Method tryMethod;

    /**
     * 二阶段提交方法
     */
    private Method confirmMethod;

    /**
     * 二阶段回滚方法
     */
    private Method cancelMethod;

}
