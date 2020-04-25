package cn.blogxin.dt.client.annotation;

import java.lang.annotation.*;

/**
 * 二阶段提交注解。
 * 分支事务提供二阶段方法时，需要在try方法上添加该注解
 *
 * @author kris
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TwoPhaseCommit {

    /**
     * 默认取注解所在方法对应的类名+方法名
     *
     * @return
     */
    String name() default "";

    /**
     * 二阶段提交的提交方法名
     *
     * @return
     */
    String confirmMethod() default "confirm";

    /**
     * 二阶段提交的取消方法名
     *
     * @return
     */
    String cancelMethod() default "cancel";

}
