package cn.blogxin.dt.client.context;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author kris
 */
public final class DTContext {

    private static final ThreadLocal<Map<String, Object>> CONTEXT = ThreadLocal.withInitial(Maps::newHashMap);

    public static void set(DTContextEnum contextEnum, Object value) {
        CONTEXT.get().put(contextEnum.getKey(), value);
    }

    public static Object get(DTContextEnum contextEnum) {
        return CONTEXT.get().get(contextEnum.getKey());
    }

    public static void clear() {
        CONTEXT.remove();
    }

}
