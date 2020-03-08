package cn.blogxin.coupon.enums;

/**
 * @author kris
 */
public enum CouponStatus {

    INIT(0),
    FREEZE(1),
    COMMIT(2);

    private int status;

    CouponStatus(int status) {
        status = this.status;
    }

    public int value() {
        return status;
    }

}
