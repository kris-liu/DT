package cn.blogxin.pay.enums;

/**
 * @author kris
 */
public enum PayChannelEnum {

    ACCOUNT(10),
    COUPON(11);

    private int status;

    PayChannelEnum(int status) {
        this.status = status;
    }

    public int value() {
        return this.status;
    }

}
