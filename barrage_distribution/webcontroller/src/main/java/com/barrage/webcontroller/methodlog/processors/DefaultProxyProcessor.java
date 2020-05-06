package com.barrage.webcontroller.methodlog.processors;


import com.barrage.webcontroller.methodlog.MethodLog;
import com.barrage.webcontroller.methodlog.core.ProxyMethodLogAnnotationParser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * bean实例化完成以后将要执行的处理器函数
 *
 * @Author TomShiDi
 * @Since 2019/6/11
 * @Version 1.0
 */
public class DefaultProxyProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz;
        Object proxyBean;
        //如果bean包含MethodLog注解，对该类进行动态代理
        if ((clazz = bean.getClass()).isAnnotationPresent(MethodLog.class)) {
            ProxyMethodLogAnnotationParser proxyMethodLogAnnotationParser = new ProxyMethodLogAnnotationParser(clazz, MethodLog.class);
            //获取cglib动态代理后的实例对象
            proxyBean = proxyMethodLogAnnotationParser.getProxyInstance(bean.getClass());
//            if (context.containsBean(beanName))
//            BeanUtils.copyProperties(bean,proxyBean);
        } else {
            proxyBean = bean;
        }
        return proxyBean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
