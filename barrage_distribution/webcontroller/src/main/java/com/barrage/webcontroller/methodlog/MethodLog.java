package com.barrage.webcontroller.methodlog;

import java.lang.annotation.*;

/**
 * 用于指定需要方法跟踪的类
 *
 * @Author TomShiDi
 */
@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLog {

    String value() default "default value";

    String preExeMessage() default "执行前的信息";

    String afterExeMessage() default "执行后的信息";

}
