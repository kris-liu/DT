package cn.blogxin.pay.enums;

/**
 * @author kris
 */
public enum PayChannelEnum {

    ACCOUNT(100),
    COUPON(101);

    private int status;

    PayChannelEnum(int status) {
        status = this.status;
    }

    public int value() {
        return status;
    }

}
