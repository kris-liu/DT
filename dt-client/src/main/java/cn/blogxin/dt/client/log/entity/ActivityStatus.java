package cn.blogxin.dt.client.log.entity;

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
     * 主事务提交已全部执行完成
     */
    COMMIT_COMPLETE(2),

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
