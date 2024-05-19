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
    BLACK_CAT_HOME_PATH("black.cat.home.path", "home目录(启动时默认设置)"),

    /**
     * 配置文件目录 ${black.cat.home.path}/config
     */
    BLACK_CAT_CONFIG_PATH("black.cat.config.path", "config目录(启动时默认设置)"),

    /**
     * 默认：${black.cat.home.path}/plugin
     */
    BLACK_CAT_PLUGIN_PATH("black.cat.plugin.path", "插件目录"),

    /**
     * 默认：${black.cat.home.path}/language
     */
    BLACK_CAT_LANGUAGE_PATH("black_cat_language_path", "语言文件配置目录"),

    LANGUAGE("language", "语言设置"),


    WINDOW_WIDTH("window.width", "主窗口宽度"),
    WINDOW_HEIGHT("window.height", "主窗口高度"),

    /**
     * 图标地址。 img/default(dark)/xxx.svg
     */
    SVG_HOME("svg.home", "主页图标地址"),
    SVG_HOME_FILL("svg.home.fill", "主页图标地址(选中)"),
    SVG_FAVORITES("svg.favorites", "收藏图标地址"),
    SVG_FAVORITES_FILL("svg.favorites.fill", "收藏图标地址(选中)"),
    SVG_TOOLBAR_FAVORITES("svg.toolbar.favorites", "工具栏收藏图标地址"),
    SVG_TOOLBAR_FAVORITES_FILL("svg.toolbar.favorites.fill", "工具栏收藏图标地址(选中)"),


    ;

    private final String key;
    private final String name;
}
