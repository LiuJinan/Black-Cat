package cn.liujinnan.tools.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @description: 语言key
 * @author: ljn
 * @create: 2024-05-02 18:22
 **/
@RequiredArgsConstructor
@Getter
public enum LanguageEnum {

    /**
     * 语言相关key
     */
    MENU_FILE("menu.file", "文件"),

    MENU_VIEW("menu.view", "视图"),

    MENU_HELP("menu.help", "帮助"),

    MENU_HELP_ABOUT("menu.help.about", "关于"),

    HOME_PLUGIN("home.plugin", "插件"),

    HOME_PLUGIN_EMPTY("home.plugin.empty","未扫描到插件"),

    /**
     * 主页工具栏刷新按钮悬停提示
     */
    HOME_PLUGIN_TOOLBAR_UPDATE_BTN_TIP("home.plugin.toolbar.update.btn.tip", "刷新"),
    ;

    private final String key;
    private final String name;
}
