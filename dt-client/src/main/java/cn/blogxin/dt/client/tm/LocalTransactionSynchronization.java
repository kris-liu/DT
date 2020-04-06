package cn.blogxin.dt.client.tm;

import cn.blogxin.dt.client.context.DTContext;
import org.springframework.transaction.support.TransactionSynchronization;

/**
 * @author kris
 */
public class LocalTransactionSynchronization implements TransactionSynchronization {
    @Override
    public void afterCompletion(int status) {
        if (status == TransactionSynchronization.STATUS_COMMITTED) {

        } else if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {

        }
        DTContext.clear();
    }
}
