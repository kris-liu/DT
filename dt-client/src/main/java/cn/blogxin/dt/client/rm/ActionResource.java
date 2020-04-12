package cn.blogxin.dt.client.rm;

import lombok.Data;

/**
 * @author kris
 */
@Data
public class ActionResource {

    private String actionName;

    private Object actionBean;

    private String tryMethod;

    private String confirmMethod;

    private String cancelMethod;


}
