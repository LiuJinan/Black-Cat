/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-04-26 11:43
 */
@RequiredArgsConstructor
@Getter
public enum PropertiesEnum {

    /**
     * 配置项key
     */
    PLUGIN_FOLDER("plugin.folder", "插件目录"),

    LANGUAGE("language", "语言配置"),


    WINDOW_WIDTH("window.width", "主窗口宽度"),
    WINDOW_HEIGHT("window.height", "主窗口高度"),

    ;

    private final String key;
    private final String name;
}
