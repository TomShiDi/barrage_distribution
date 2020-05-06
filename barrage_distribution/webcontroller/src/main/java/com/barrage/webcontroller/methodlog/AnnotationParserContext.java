package com.barrage.webcontroller.methodlog;

import com.barrage.webcontroller.methodlog.core.AnnotationParserMeta;
import com.barrage.webcontroller.methodlog.core.AnnotationScannedMeta;
import com.barrage.webcontroller.methodlog.factorys.ProxyFactoryMeta;

import java.util.HashMap;
import java.util.Map;

public class AnnotationParserContext implements ContextMeta {

    private static Map<Class<? extends AnnotationParserMeta>, AnnotationParserMeta> annotationParserMetaMap;

    private AnnotationScannedMeta annotationScannedMeta;

    private AnnotationParserMeta annotationParserMeta;

    private ProxyFactoryMeta proxyFactoryMeta;

    public AnnotationParserContext() {
        if (annotationParserMetaMap == null) {
            annotationParserMetaMap = new HashMap<>();
        }
    }

    public void addAnnotationParser(AnnotationParserMeta annotationParserMeta) {
        annotationParserMetaMap.put(annotationParserMeta.getClass(), annotationParserMeta);
    }

    public AnnotationParserMeta getAnnotationParser(Class<? extends AnnotationParserMeta> pointAnnotationParser) {
        return annotationParserMetaMap.get(pointAnnotationParser);
    }

    public AnnotationScannedMeta getAnnotationScannedMeta() {
        return annotationScannedMeta;
    }

    public void setAnnotationScannedMeta(AnnotationScannedMeta annotationScannedMeta) {
        this.annotationScannedMeta = annotationScannedMeta;
    }

    public AnnotationParserMeta getAnnotationParserMeta() {
        return annotationParserMeta;
    }

    public void setAnnotationParserMeta(AnnotationParserMeta annotationParserMeta) {
        this.annotationParserMeta = annotationParserMeta;
    }

    public ProxyFactoryMeta getProxyFactoryMeta() {
        return proxyFactoryMeta;
    }

    public void setProxyFactoryMeta(ProxyFactoryMeta proxyFactoryMeta) {
        this.proxyFactoryMeta = proxyFactoryMeta;
    }
}
