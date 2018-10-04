package cn.blogxin.dt.client.annotation;

import java.lang.annotation.*;

/**
 * @author kris
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DT {
}
