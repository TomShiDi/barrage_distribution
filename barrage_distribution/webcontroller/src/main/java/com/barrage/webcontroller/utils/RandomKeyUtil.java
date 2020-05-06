package com.barrage.webcontroller.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/3/20
 **/
public class RandomKeyUtil {

    private static Random random = null;

    private static int bound = 10;

    /**
     * 生成6位数字随机码
     *
     * @return 随机码字符串
     */
    public static String getRandomNum() {
        if (random == null) {
            synchronized (RandomKeyUtil.class) {
                if (random == null) {
                    random = new Random();
                }
            }
        }
        return getRandomNum(6);
    }

    /**
     * 生成指定长度的随机码
     *
     * @param length 长度
     * @return 指定长度的随机码字符串
     */
    public static String getRandomNum(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(random.nextInt(bound));
        }
        return stringBuffer.toString();
    }

    /**
     * UUID外加时间戳，保证token唯一
     *
     * @return
     */
    public static String getUniqueUuid() {
        String originUuid = UUID.randomUUID().toString();
        originUuid = originUuid.replace("-", "");
        String timeMills = System.currentTimeMillis() + "";
        originUuid = originUuid + timeMills.substring(timeMills.length() - 5);
        return originUuid;
    }
}
