package cn.blogxin.dt.client.constant;

/**
 * 常量池
 *
 * @author kris
 */
public interface Constant {

    /**
     * 下划线
     */
    String UNDERSCORE = "_";

    /**
     * job的zk根namespace
     */
    String DT_COORDINATOR_JOB_BASE_NAMESPACE = "dt_coordinator_job/";

    /**
     * 超时后重试的间隔时间
     */
    int TIMEOUT_RETRY_EXECUTION_TIME = 10;

}
