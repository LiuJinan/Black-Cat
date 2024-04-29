/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ext.plugin.constant;


public enum PluginPropertiesEnum {

    /**
     * 插件配置信息
     */
    PLUGIN_AUTHOR("plugin.author", "插件作者", ""),

    PLUGIN_VERSION("plugin.version", "插件版本号", "v1"),

    ;

    PluginPropertiesEnum(String key, String name, String defaultValue) {
        this.key = key;
        this.name = name;
        this.defaultValue = defaultValue;
    }

    private String key;
    private String name;

    private String defaultValue;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
