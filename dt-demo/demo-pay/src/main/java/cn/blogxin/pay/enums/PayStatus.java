package cn.blogxin.pay.enums;

/**
 * @author kris
 */
public enum PayStatus {

    INIT(0),
    SUCCESS(1);

    private int status;

    PayStatus(int status) {
        status = this.status;
    }

    public int value() {
        return status;
    }

}
