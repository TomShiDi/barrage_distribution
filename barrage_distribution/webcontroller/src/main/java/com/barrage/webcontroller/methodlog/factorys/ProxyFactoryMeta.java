package com.barrage.webcontroller.methodlog.factorys;

public interface ProxyFactoryMeta {

    /**
     * get the proxy instance represent the original class
     *
     * @return
     */
    Object getInstance(Class<?> superClass);
}
