package com.barrage.webcontroller.methodlog.core;

import java.util.Set;

public interface AnnotationScannedMeta {

    Set<Class<?>> scannedCandidates(String pkgName);

}
