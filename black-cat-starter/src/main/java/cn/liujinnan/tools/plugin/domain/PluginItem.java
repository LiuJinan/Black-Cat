/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.plugin.domain;

import cn.liujinnan.tools.ext.plugin.Plugin;
import lombok.Data;

import javax.swing.*;

/**
 * @description: 插件子项
 * @author: liujinnan
 * @create: 2024-04-26 00:50
 **/
@Data
public class PluginItem {

    /**
     * 全限定类名, 包含包路径。例：cn.liujinnan.tools.plugin.domain。PluginItem
     */
    private String className;

    /**
     * 类名
     */
    private String simpleClassName;

    /**
     * 组件名称.
     * @PluginComponent.name
     */
    private String componentName;

    /**
     * 图标
     */
    private Icon icon;

    private Plugin plugin;

    /**
     * plugin.getJComponent(), 无需创建多次实例，加载时初始化
     */
    private JComponent jComponent;

}
