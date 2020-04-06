package cn.blogxin.dt.client.context;

/**
 * @author kris
 */
public enum DTContextEnum {

    XID("xid"),
    START_TIME("startTime");

    private String key;

    DTContextEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }


}
