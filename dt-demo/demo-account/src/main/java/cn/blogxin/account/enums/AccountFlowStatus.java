package cn.blogxin.account.enums;

/**
 * @author kris
 */
public enum AccountFlowStatus {

    FREEZE(0),
    COMMIT(1),
    UNFREEZE(2);

    private int status;

    AccountFlowStatus(int status) {
        this.status = status;
    }

    public int value() {
        return this.status;
    }

}
