package com.barrage.webcontroller.methodlog.core;


import com.barrage.webcontroller.methodlog.utils.ClassScanUtil;

import java.util.HashSet;
import java.util.Set;

public class AnnotationScannedFullAchieve implements AnnotationScannedMeta {

    private Set<Class<?>> candidateClass;

    @Override
    public Set<Class<?>> scannedCandidates(String pkgName) {
        getClass(pkgName);
        return this.candidateClass;
    }

    private void getClass(String pkgName) {
        if (this.candidateClass == null) {
            this.candidateClass = new HashSet<>();
        }
        ClassScanUtil.getClass(pkgName, this.candidateClass);
    }
}
