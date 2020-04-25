package cn.blogxin.dt.client.tm;

/**
 * 分布式事务管理器。
 * 控制分布式事务的边界，负责开启一个分布式事务，并最终发起分布式事务提交或回滚的决议。
 *
 * @author kris
 */
public interface TransactionManager {

    /**
     * 开始分布式事务
     *
     * @param xidSuffix 事务id后缀。有分库分表需求时使用，用来保证事务记录与业务记录在同一个分库分表中
     */
    void start(String xidSuffix);

    /**
     * 提交分布式事务
     *
     * @return 返回true，返回false时需要由TC重试，重试失败需要人工干预
     */
    boolean commit();

    /**
     * 回滚分布式事务
     *
     * @return 返回true，返回false时需要由TC重试，重试失败需要人工干预
     */
    boolean rollback();

}
