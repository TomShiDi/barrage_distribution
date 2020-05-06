package com.barrage.webcontroller.methodlog.core;


import com.barrage.webcontroller.methodlog.MethodLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ProxyMethodLogAnnotationParser implements AnnotationParserMeta, MethodInterceptor {

    private Set<Class<?>> candidateClassSet;

    private Set<Class<?>> pointAnnotatedClassSet;

    private Annotation customAnnotation;

    private Class<? extends Annotation> pointAnnotationClass;

    private Enhancer enhancer;

    private Logger logger = LoggerFactory.getLogger(ProxyMethodLogAnnotationParser.class);

    public ProxyMethodLogAnnotationParser(Set<Class<?>> candidateClassSet, Class<? extends Annotation> pointAnnotationClass) {
        this.candidateClassSet = candidateClassSet;
        this.pointAnnotationClass = pointAnnotationClass;
        doParse();
    }

    public ProxyMethodLogAnnotationParser(Class<?> pointedClass, Class<? extends Annotation> pointedAnnotationClass) {
        this(new HashSet<Class<?>>(2) {
            {
                super.add(pointedClass);
            }
        }, pointedAnnotationClass);
    }

    @Override
    public void doParse() {
        preDoParse();
        getPointAnnotatedClass();
        afterDoParse();
    }

    @Override
    public Set<Class<?>> getPointedClasses() {
        return this.pointAnnotatedClassSet;
    }

    /**
     * default do nothing
     */
    @Override
    public void preDoParse() {

    }

    /**
     * default do nothing
     */
    @Override
    public void afterDoParse() {

    }

    @Override
    public void preInvokeSuperClassMethod(Method method) {
        MethodLog methodLog = (MethodLog) customAnnotation;
        logger.info("before: --{}-- executed : {}", method.getName(), methodLog.afterExeMessage());
    }

    @Override
    public void afterInvokeSuperClassMethod(Method method, Object... params) {
        MethodLog methodLog = (MethodLog) customAnnotation;
        logger.info("after: --{}-- executed : {}", method.getName(), methodLog.afterExeMessage());
    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        preInvokeSuperClassMethod(method);
        Object result = proxy.invokeSuper(target, args);
        afterInvokeSuperClassMethod(method);
        return result;
    }

    private void getPointAnnotatedClass() {
        if (pointAnnotatedClassSet == null) {
            pointAnnotatedClassSet = new HashSet<>();
        }
        for (Class<?> clazz : candidateClassSet) {
            if (clazz.getAnnotation(pointAnnotationClass) != null) {
                this.pointAnnotatedClassSet.add(clazz);
            }
        }
    }


    private void initEnhancer(Class superClass) {
        this.enhancer = new Enhancer();
        this.enhancer.setCallback(this);
        this.enhancer.setSuperclass(superClass);
    }

    private void getSuperClassAnnotation(Class superClass, Class<? extends Annotation> pointAnnotationClass) throws Exception {
        this.customAnnotation = superClass.getAnnotation(pointAnnotationClass);
        if (customAnnotation == null) {
            throw new Exception();
        }
    }

    @Override
    public Object getProxyInstance(Class<?> clazz) {
        if (!pointAnnotatedClassSet.contains(clazz)) {
            Object result = null;
            try {
                result = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        initEnhancer(clazz);
        try {
            getSuperClassAnnotation(clazz, this.pointAnnotationClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enhancer.create();
    }

    private ClassLoader getClassLoader() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ProxyMethodLogAnnotationParser.class.getClassLoader();
        }
        if (classLoader == null) {
            throw new Exception();
        }
        return classLoader;
    }
}
