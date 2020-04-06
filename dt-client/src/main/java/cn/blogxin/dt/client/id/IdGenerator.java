package cn.blogxin.dt.client.id;

import cn.blogxin.dt.client.constant.Constant;
import org.apache.commons.lang3.StringUtils;

/**
 * 事务ID生成器
 *
 * @author kris
 */
public interface IdGenerator {

    /**
     * 生成事务ID
     *
     * @return
     */
    String getId();

    /**
     * 生成带后缀的事务ID
     *
     * @param suffix 事务ID后缀，一般需要匹配分库分表规则时使用
     * @return
     */
    default String getId(String suffix) {
        if (StringUtils.isNotBlank(suffix)) {
            return getId() + Constant.UNDERSCORE + suffix;
        } else {
            return getId();
        }
    }

}
