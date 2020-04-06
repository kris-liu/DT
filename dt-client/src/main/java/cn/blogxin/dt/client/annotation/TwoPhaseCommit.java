package cn.blogxin.dt.client.annotation;

import java.lang.annotation.*;

/**
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
