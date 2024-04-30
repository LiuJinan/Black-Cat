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
        JMenuBar jMenuBar = new JMenuBar();
        jf.setJMenuBar(jMenuBar);
        JMenu jMenu = new JMenu("file");
        jMenuBar.add(jMenu);
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
        JTabbedPane menu = new JTabbedPane();
        menu.setTabPlacement(JTabbedPane.LEFT);
        jf.setContentPane(menu);
//        String path = MainFrame.class.getResource("/info.png").getPath();
//        tabbedPane.addTab("aa", new ImageIcon(path), jPanel);
        JPanel home = new JPanel(new GridLayout(1, 2));

        menu.addTab("主页", home);
        JTabbedPane homeTabbedPane = new JTabbedPane();
        homeTabbedPane.setTabPlacement(JTabbedPane.TOP);
        homeTabbedPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        home.add(homeTabbedPane);

        menu.addTab("帮助", new JPanel());
        JPanel setting = new JPanel();
        menu.addTab("设置", setting);
//        menu.setSize(100,100);
        menu.setFont(new Font(null, Font.PLAIN, 20));

        showPluginItem(homeTabbedPane);
    }

    private static void showPluginItem(JTabbedPane homeTabbedPane) {
        try {
            PluginManager pluginManager = PluginManager.getInstance();
            List<PluginClassLoader> pluginClassLoaderList = pluginManager.getPluginClassLoaderList();
            pluginClassLoaderList.forEach(pluginClassLoader -> {
                PluginJarInfo pluginJarInfo = pluginClassLoader.getPluginJarInfo();
                try {
                    List<PluginItem> pluginItemList = pluginJarInfo.getPluginItemList();
                    pluginItemList.forEach(pluginItem -> {
                        // TODO: 2024/4/30 图标
                        homeTabbedPane.addTab(pluginItem.getComponentName(), pluginItem.getPlugin().getJComponent());
                        log.info("load : {}", pluginItem.getClassName());
                    });
                } catch (Exception e) {
                    log.error("load plugin item error. jar={}", pluginJarInfo.getJarName(), e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
