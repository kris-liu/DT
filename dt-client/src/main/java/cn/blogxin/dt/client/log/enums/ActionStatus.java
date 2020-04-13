package cn.blogxin.dt.client.log.enums;

/**
 * @author kris
 */
public enum ActionStatus {

    /**
     * 分支事务初始化
     */
    INIT(0),

    /**
     * 分支事务提交完成
     */
    COMMIT(1),

    /**
     * 分支事务回滚完成
     */
    ROLLBACK(3);

    private int status;

    ActionStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }


}
