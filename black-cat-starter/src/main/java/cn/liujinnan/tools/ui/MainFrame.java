/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ui;

import cn.liujinnan.tools.constant.PropertiesEnum;
import cn.liujinnan.tools.plugin.PluginClassLoader;
import cn.liujinnan.tools.plugin.PluginManager;
import cn.liujinnan.tools.plugin.domain.PluginItem;
import cn.liujinnan.tools.plugin.domain.PluginJarInfo;
import cn.liujinnan.tools.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-04-25 16:51
 */
@Slf4j
public class MainFrame {

    /**
     * 显示主窗口
     */
    public static void showMainFrame(){

        PropertiesUtils instance = PropertiesUtils.getInstance();


        JFrame jf = new JFrame("Black-Cat");
        // 设置窗口大小
        Integer width = instance.getIntValue(PropertiesEnum.WINDOW_WIDTH.getKey());
        Integer height = instance.getIntValue(PropertiesEnum.WINDOW_HEIGHT.getKey());
        jf.setSize(width, height);
        // 把窗口位置设置到屏幕中心
        jf.setLocationRelativeTo(null);
        // 当点击窗口的关闭按钮时退出程序
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setVisible(true);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.getImage(MainFrame.class.getResource("/logo.png"));
        jf.setIconImage(image);

        // 创建选项卡
        JTabbedPane tabbedPane = new JTabbedPane();
        jf.setContentPane(tabbedPane);
        JPanel jPanel = new JPanel();
        jPanel.add(new JButton("aa测试"));
//        String path = MainFrame.class.getResource("/info.png").getPath();
//        tabbedPane.addTab("aa", new ImageIcon(path), jPanel);
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);

        try {
            PluginManager pluginManager = PluginManager.getInstance();
            List<PluginClassLoader> pluginClassLoaderList = pluginManager.getPluginClassLoaderList();
            pluginClassLoaderList.forEach(pluginClassLoader -> {
                try {
                    PluginJarInfo pluginJarInfo = pluginClassLoader.getPluginJarInfo();
                    List<PluginItem> pluginItemList = pluginJarInfo.getPluginItemList();
                    pluginItemList.forEach(pluginItem -> {
                        tabbedPane.addTab(pluginItem.getComponentName(), pluginItem.getPlugin().getJComponent());
                    });
                } catch (Exception e) {

                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }
        JPanel setting = new JPanel();
//        setting.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        tabbedPane.addTab("设置", setting);

    }
}
