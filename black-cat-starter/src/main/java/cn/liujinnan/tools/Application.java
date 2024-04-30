/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools;

import cn.liujinnan.tools.ui.MainFrame;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.IntelliJTheme;

import javax.swing.*;
import java.awt.*;

/**
 * @description: 启动类
 * @author: liujinnan
 * @create: 2024-04-26 01:28
 **/
public class Application {

    public static void main(String[] args) throws Exception{
//        UIManager.setLookAndFeel(new FlatDarculaLaf());
        UIManager.setLookAndFeel(new FlatIntelliJLaf());
//        UIManager.put( "TabbedPane.showTabSeparators", true );
//        UIManager.put( "TabbedPane.selectedBackground", Color.white );
//        UIManager.put( "TabbedPane.tabSeparatorsFullHeight", true );
        // https://www.formdev.com/flatlaf/themes/
        System.setProperty("flatlaf.menuBarEmbedded", "true");
        IntelliJTheme.setup( Application.class.getResourceAsStream(
                "/themes/RiderLight.theme.json" ) );
        MainFrame.showMainFrame();
    }
}
