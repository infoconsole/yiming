package com.mitix.yiming;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 创建于:2014-12-5<br>
 * 版权所有(C) 2014 深圳市银之杰科技股份有限公司<br>
 * 获取随机UUID
 *
 * @author honglvhang
 * @version 1.0.0.0
 */
public class SIDUtil {

    public static String getUUID16() {
        boolean succ = false;
        String uuid16 = null;
        while (!succ) {
            uuid16 = getUUID();
            if (!StringUtils.isNumeric(uuid16.substring(0, 1))) {
                succ = true;
                uuid16 = uuid16.substring(0, 16);
            }
        }
        return uuid16;
    }

    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().toLowerCase().replaceAll("-", "")
                .replaceAll(" ", "");

    }
}
