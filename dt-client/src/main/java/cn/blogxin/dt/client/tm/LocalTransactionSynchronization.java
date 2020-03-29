package cn.blogxin.dt.client.tm;

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
    }
}
