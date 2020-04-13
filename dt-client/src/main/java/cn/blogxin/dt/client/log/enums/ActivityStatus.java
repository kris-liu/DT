package cn.blogxin.dt.client.log.enums;

/**
 * @author kris
 */
public enum ActivityStatus {

    /**
     * 主事务初始化
     */
    INIT(0),

    /**
     * 主事务提交
     */
    COMMIT(1),

    /**
     * 主事务提交，分支事务全部执行完成
     */
    COMMIT_FINISH(2),

    /**
     * 主事务回滚
     */
    ROLLBACK(3);

    private int status;

    ActivityStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }


}
