package cn.blogxin.dt.client.aop;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author kris
 */
@Data
public class ActionResource {

    private String actionName;

    private Object actionBean;

    private Method tryMethod;

    private Method confirmMethod;

    private Method cancelMethod;


}
