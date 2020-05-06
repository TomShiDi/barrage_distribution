package com.barrage.webcontroller.methodlog.utils;

/**
 * @Author TomShiDi
 * @Since 2019/6/13
 * @Version 1.0
 */
public class BeanNameUtil {

    public static String parseToBeanName(String srcName) {
//        String dstName;
        if (srcName == null || srcName.length() < 1) {
            throw new RuntimeException();
        }
        if (srcName.contains(".")) {
            String temp = srcName.substring(srcName.lastIndexOf(".") + 1, srcName.length());
            if (!(Character.isLowerCase(temp.charAt(0)))) {
                return (new StringBuilder()).append(Character.toLowerCase(temp.charAt(0))).append(temp.substring(1)).toString();
            } else {
                return temp;
            }
        } else {
            if (!(Character.isLowerCase(srcName.charAt(0)))) {
                return (new StringBuilder()).append(Character.toLowerCase(srcName.charAt(0))).append(srcName.substring(1)).toString();
            } else {
                return srcName;
            }
        }
    }
}
