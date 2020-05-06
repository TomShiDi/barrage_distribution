package com.barrage.webcontroller.methodlog;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ClassScanUtil {

    public static void getClass(String pkgName, Set<Class<?>> classSet) {
        if (classSet == null) {
            classSet = new HashSet<>();
        }
        String packageName = pkgName;
        String packageUrlName = packageName.replace(".", "/");
        Enumeration<URL> urlEnumeration;

        try {
            urlEnumeration = Thread.currentThread().getContextClassLoader().getResources(packageUrlName);
            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String path = URLDecoder.decode(url.getFile(), "utf-8");
                    findClassByFile(packageName, path, classSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void findClassByFile(String pkgName, String pkgPath, Set<Class<?>> classSet) throws Exception {
        if (classSet == null) {
            classSet = new HashSet<>();
        }
        File root = new File(pkgPath);
        File[] fileOrDirArray = root.listFiles();

        if (fileOrDirArray == null || fileOrDirArray.length == 0) {
            return;
        }

        String className;
        for (File file : fileOrDirArray) {
            if (file.isDirectory()) {
                findClassByFile(pkgName + "." + file.getName(), pkgPath + "/" + file.getName(), classSet);
                continue;
            }

            className = file.getName();
            className = className.substring(0, className.length() - 6);

            Class<?> clazz = loadClassByName(pkgName + "." + className);
            classSet.add(clazz);
        }
    }

    public static Class<?> loadClassByName(String name) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassScanUtil.class.getClassLoader();
        }
        if (classLoader == null) {
            throw new Exception("当前无可用的ClassLoader");
        }
        return classLoader.loadClass(name);
    }
}
