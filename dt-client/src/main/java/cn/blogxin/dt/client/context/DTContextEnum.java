package cn.blogxin.dt.client.context;

/**
 * @author kris
 */
public enum DTContextEnum {

    XID("xid");

    private String key;

    DTContextEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }


}
