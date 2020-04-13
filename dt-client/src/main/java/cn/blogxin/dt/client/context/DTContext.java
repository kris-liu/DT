package cn.blogxin.dt.client.context;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author kris
 */
public final class DTContext {

    private static final ThreadLocal<Map<DTContextEnum, Object>> CONTEXT = ThreadLocal.withInitial(Maps::newHashMap);

    public static void set(DTContextEnum contextEnum, Object value) {
        CONTEXT.get().put(contextEnum, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(DTContextEnum contextEnum) {
        return (T) contextEnum.getKeyClass().cast(CONTEXT.get().get(contextEnum));
    }

    public static boolean inTransaction() {
        return CONTEXT.get().get(DTContextEnum.XID) != null;
    }

    public static void clear() {
        CONTEXT.remove();
    }

}
