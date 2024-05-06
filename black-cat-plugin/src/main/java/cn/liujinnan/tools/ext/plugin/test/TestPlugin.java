/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ext.plugin.test;

import cn.liujinnan.tools.ext.plugin.Plugin;
import cn.liujinnan.tools.ext.plugin.annotation.PluginComponent;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-05-06 14:30
 */
public class TestPlugin {

    public static void runTest(Plugin plugin){
        runTest(plugin, 500, 500);
    }

    public static void runTest(Plugin plugin, int width, int height){
        if (Objects.isNull(plugin)) {
            return;
        }
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setSize(width, height);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
        JTabbedPane tabbedPane = new JTabbedPane();
        jFrame.setContentPane(tabbedPane);

        PluginComponent annotation = plugin.getClass().getAnnotation(PluginComponent.class);
        if (Objects.isNull(annotation)) {
            System.out.println("PluginComponent is null");
            return;
        }
        ImageIcon imageIcon = null;
        if (StringUtils.isNotBlank(annotation.icon())) {
            ImageIcon tmp = new ImageIcon("/" + annotation.icon());
            Image scaledInstance = tmp.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledInstance);
        }

        tabbedPane.addTab(annotation.name(), imageIcon, plugin.getJComponent());
    }
}
