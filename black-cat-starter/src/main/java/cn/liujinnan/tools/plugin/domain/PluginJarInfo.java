/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.plugin.domain;

import lombok.Data;

import java.util.List;

/**
 * @description: 插件jar信息
 * @author: liujinnan
 * @create: 2024-04-26 00:48
 **/
@Data
public class PluginJarInfo {

    private String jarName;

    private String version;

    private String author;

    /**
     * 绝对路径
     */
    private String jarPath;

    private List<PluginItem> pluginItemList;
}
