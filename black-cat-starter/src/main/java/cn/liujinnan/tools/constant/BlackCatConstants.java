/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.constant;

import java.io.File;

/**
 * 常量
 * @author ljn
 * @version 1.0
 * @date 2024-05-15 9:58
 */
public class BlackCatConstants {

    private BlackCatConstants(){
        throw new IllegalStateException();
    }


    /**
     * 用户目录
     */
    public static String USER_HOME_PATH = System.getProperty("user.home") + File.separator + "BlackCat" + File.separator;

    public static String FAVORITES_CONFIG_FILE_PATH = USER_HOME_PATH + "favorites.json";
}
