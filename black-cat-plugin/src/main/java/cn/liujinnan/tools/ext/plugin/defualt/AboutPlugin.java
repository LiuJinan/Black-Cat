/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ext.plugin.defualt;

import cn.liujinnan.tools.ext.plugin.Plugin;
import cn.liujinnan.tools.ext.plugin.annotation.PluginComponent;

import javax.swing.*;

/**
 * @description: 用于介绍当前插件信息
 * @author: liujinnan
 * @create: 2024-04-26 01:23
 **/
@PluginComponent(name = "about")
public class AboutPlugin implements Plugin {
    @Override
    public JComponent getJComponent() {
        return null;
    }
}
