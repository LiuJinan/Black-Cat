/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ui;

import cn.liujinnan.tools.Application;
import cn.liujinnan.tools.constant.PropertiesEnum;
import cn.liujinnan.tools.ui.component.ComponentIcon;
import cn.liujinnan.tools.ui.favorites.FavoritesUi;
import cn.liujinnan.tools.ui.home.HomeUi;
import cn.liujinnan.tools.ui.menu.MenuBarUi;
import cn.liujinnan.tools.utils.PropertiesUtils;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-04-25 16:51
 */
@Slf4j
public class MainFrame {

    private static final JFrame JF = new JFrame("Black-Cat");

    public static JFrame getMainFrame() {
        return JF;
    }

    /**
     * 显示主窗口
     */
    public static void showMainFrame() {
//        setUpTheme();

        PropertiesUtils instance = PropertiesUtils.getInstance();

        // 菜单
        JF.setJMenuBar(new MenuBarUi());
        // 设置窗口大小
        Integer width = instance.getIntValue(PropertiesEnum.WINDOW_WIDTH.getKey());
        Integer height = instance.getIntValue(PropertiesEnum.WINDOW_HEIGHT.getKey());
        JF.setSize(width, height);
        // 把窗口位置设置到屏幕中心
        JF.setLocationRelativeTo(null);
        // 当点击窗口的关闭按钮时退出程序
        JF.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 窗口图标
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.getImage(MainFrame.class.getResource("/img/logo.png"));
        JF.setIconImage(image);

        // 默认选项卡颜色
        UIManager.put("TabbedPane.tabType", "card");
        UIManager.put("TabbedPane.hasFullBorder", true);
        UIManager.put("TabbedPane.showTabSeparators", false);

        // 主页
        HomeUi homeUi = new HomeUi();
        //收藏
        FavoritesUi favoritesUi = new FavoritesUi();


//        Object hoverColor = UIManager.get("TabbedPane.hoverColor");
//        Object selectedBackground = UIManager.get("TabbedPane.selectedBackground");
//        Object underlineColor = UIManager.get("TabbedPane.underlineColor");
//        Object inactiveUnderlineColor = UIManager.get("TabbedPane.inactiveUnderlineColor");
//        Object focusColor = UIManager.get("TabbedPane.focusColor");
        UIManager.put("TabbedPane.hoverColor", UIManager.get("TabbedPane.background"));
        UIManager.put("TabbedPane.selectedBackground", UIManager.get("TabbedPane.background"));
        UIManager.put("TabbedPane.underlineColor", UIManager.get("TabbedPane.background"));
        UIManager.put("TabbedPane.inactiveUnderlineColor", UIManager.get("TabbedPane.background"));
        UIManager.put("TabbedPane.focusColor", UIManager.get("TabbedPane.background"));

        // 左侧菜单选项卡
        JTabbedPane leftPane = new JTabbedPane();
        leftPane.putClientProperty("JTabbedPane.showTabSeparators", false);

        changeIcon(leftPane);
        reloadFavorites(leftPane);
        leftPane.setTabPlacement(JTabbedPane.LEFT);
        JF.setContentPane(leftPane);
        // 首行为空
        leftPane.addTab("", new JLabel());
        leftPane.setEnabledAt(0, false);

        leftPane.addTab("", homeUi.getIconNotSelected(), homeUi);
        leftPane.setSelectedIndex(1);


//        leftPane.addTab("", new JLabel());
//        leftPane.setEnabledAt(2, false);


        leftPane.addTab("", favoritesUi.getIconNotSelected(), favoritesUi);
        JF.setVisible(true);
    }


    private static void reloadFavorites(JTabbedPane tabbedPane) {
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedComponent() instanceof FavoritesUi favoritesUi) {
                    favoritesUi.reload();
                }
            }
        });
    }


    private static void changeIcon(JTabbedPane tabbedPane) {
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Component select = tabbedPane.getSelectedComponent();
                for (Component component : tabbedPane.getComponents()) {
                    int index = tabbedPane.indexOfComponent(component);
                    if (index < 0) {
                        continue;
                    }
                    if (component instanceof ComponentIcon tmp) {
                        Icon icon = tmp.getIcon(select == component);
                        tabbedPane.setIconAt(index, icon);
                    }
                }
            }
        });
    }

    private static void setUpTheme() {
        //标题栏菜单
        System.setProperty("flatlaf.menuBarEmbedded", "true");

//        UIManager.setLookAndFeel(new FlatDarculaLaf());
//        UIManager.setLookAndFeel(new FlatIntelliJLaf());
        // https://www.formdev.com/flatlaf/themes/

//        String themeName = "/themes/oneDark/one_dark.theme.json";
        String themeName = "/themes/expUi/expUI_light.theme.json";
//        String themeName = "/themes/expUi/expUI_dark.theme.json";
        IntelliJTheme.setup(Application.class.getResourceAsStream(themeName));
        PropertiesUtils instance = PropertiesUtils.getInstance();

        instance.put(PropertiesEnum.SVG_HOME.getKey(), new FlatSVGIcon("img/default/home.svg", 30, 30));
        instance.put(PropertiesEnum.SVG_HOME_FILL.getKey(), new FlatSVGIcon("img/default/home-fill.svg", 30, 30));
        instance.put(PropertiesEnum.SVG_FAVORITES.getKey(), new FlatSVGIcon("img/default/favorites.svg", 30, 30));
        instance.put(PropertiesEnum.SVG_FAVORITES_FILL.getKey(), new FlatSVGIcon("img/default/favorites-fill.svg", 30, 30));
        instance.put(PropertiesEnum.SVG_TOOLBAR_FAVORITES.getKey(), new FlatSVGIcon("img/default/toolbar/favorites.svg", 20, 20));
        instance.put(PropertiesEnum.SVG_TOOLBAR_FAVORITES_FILL.getKey(), new FlatSVGIcon("img/default/toolbar/favorites-fill.svg", 20, 20));
        if (themeName.contains("dark")) {
            instance.put(PropertiesEnum.SVG_HOME.getKey(), new FlatSVGIcon("img/dark/home.svg", 30, 30));
            instance.put(PropertiesEnum.SVG_HOME_FILL.getKey(), new FlatSVGIcon("img/dark/home-fill.svg", 30, 30));
            instance.put(PropertiesEnum.SVG_FAVORITES.getKey(), new FlatSVGIcon("img/dark/favorites.svg", 30, 30));
            instance.put(PropertiesEnum.SVG_FAVORITES_FILL.getKey(), new FlatSVGIcon("img/dark/favorites-fill.svg", 30, 30));
            instance.put(PropertiesEnum.SVG_TOOLBAR_FAVORITES.getKey(), new FlatSVGIcon("img/dark/toolbar/favorites.svg", 20, 20));
            instance.put(PropertiesEnum.SVG_TOOLBAR_FAVORITES_FILL.getKey(), new FlatSVGIcon("img/dark/toolbar/favorites-fill.svg", 20, 20));
        }
    }


}
