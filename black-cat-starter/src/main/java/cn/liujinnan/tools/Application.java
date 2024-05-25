/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools;

import cn.liujinnan.tools.constant.PropertiesEnum;
import cn.liujinnan.tools.ui.MainFrame;
import cn.liujinnan.tools.utils.PropertiesUtils;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

/**
 * @description: 启动类
 * @author: liujinnan
 * @create: 2024-04-26 01:28
 **/
public class Application {

    public static void main(String[] args) throws Exception {

//        UIManager.put( "TabbedPane.showTabSeparators", true );
//        UIManager.put( "TabbedPane.selectedBackground", Color.white );
//        UIManager.put( "TabbedPane.tabSeparatorsFullHeight", true );
        setUpTheme();
        MainFrame.showMainFrame();
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
