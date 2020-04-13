package cn.blogxin.dt.client.context;

import cn.blogxin.dt.client.log.entity.Action;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @author kris
 */
@Data
public class ActionContext {

    private Map<String, Action> actionMap = Maps.newHashMap();

    public void put(String actionName, Action action) {
        actionMap.put(actionName, action);
    }

    public Action get(String actionName) {
        return actionMap.get(actionName);
    }

}
