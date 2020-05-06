package com.barrage.webcontroller.methodlog.core;

import java.lang.reflect.Method;
import java.util.Set;

public interface AnnotationParserMeta {

    /**
     * do the custom parse method
     */
    void doParse();

    /**
     * get the Class you want to parse
     *
     * @return
     */
    Set<Class<?>> getPointedClasses();

    /**
     * the custom method do before method doParse
     */
    void preDoParse();

    /**
     * the custom method do after method doParse
     */
    void afterDoParse();

    /**
     * this method will be executed before superClass's method
     *
     * @param method the method which is executed
     */
    void preInvokeSuperClassMethod(Method method);

    /**
     * this method will be executed after superClass's method
     *
     * @param method the method which is executed
     * @param params some params that need to handle
     */
    void afterInvokeSuperClassMethod(Method method, Object... params);

    /**
     * create proxy instance
     */
    Object getProxyInstance(Class<?> clazz);
}
