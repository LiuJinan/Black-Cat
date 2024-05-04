package cn.liujinnan.tools.ui.home;

import cn.liujinnan.tools.plugin.domain.PluginJarInfo;
import cn.liujinnan.tools.utils.ColorUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @description: home页面工具栏
 * @author: ljn
 * @create: 2024-05-04 12:14
 **/
public class HomeToolBar extends JPanel {

    private final JToolBar jToolBar = new JToolBar();

    /**
     * 当前工具栏对应的jar插件
     */
    private PluginJarInfo pluginJarInfo;
    /**
     * jar插件对应的plugin标签
     */
    private JTabbedPane itemPane;

    public HomeToolBar(PluginJarInfo pluginJarInfo, JTabbedPane itemPane) {
        this.pluginJarInfo = pluginJarInfo;
        this.itemPane = itemPane;
        init();
    }

    private void init(){
        this.setLayout(new GridLayout(1,1));
        this.add(jToolBar);
        // 工具栏背景色加深10%
        jToolBar.setBackground(ColorUtils.darkenColor(jToolBar.getBackground(), 0.1));
        jToolBar.add(new JButton("刷新"));
    }
}
