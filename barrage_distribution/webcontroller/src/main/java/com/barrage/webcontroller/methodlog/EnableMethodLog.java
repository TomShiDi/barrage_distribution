package com.barrage.webcontroller.methodlog;

import com.barrage.webcontroller.methodlog.processors.DefaultProxyProcessor;
import com.barrage.webcontroller.methodlog.registrys.MethodLogResourceRegistry;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 动态注册bean，起作用的是@Import这个注解
 *
 * @Author TomShiDi
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({MethodLogResourceRegistry.class, DefaultProxyProcessor.class})
public @interface EnableMethodLog {

    /**
     * 待扫描的包名
     *
     * @return
     */
    String scannedPkgName();

}
