package cn.liujinnan.tools.ui.home;

import cn.liujinnan.tools.plugin.domain.PluginJarInfo;
import cn.liujinnan.tools.utils.ColorUtils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

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

        JButton update = new JButton();
        URL updatePng = this.getClass().getResource("/img/update.png");
        Image scaledInstance = new ImageIcon(updatePng).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        update.setIcon(new ImageIcon(scaledInstance));
//        update.setIcon(new FlatSearchIcon());
//        update.putClientProperty("JButton.buttonType", "help");
//        UIManager.put("JButton.buttonType", "help");


        jToolBar.add(update);
        // TODO: 2024-05-04 刷新功能开发 
    }
}
