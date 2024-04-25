/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.black.cat;

import cn.liujinnan.tools.black.cat.frame.MainFrame;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-04-25 16:47
 */
public class Application {

    public static void main(String[] args) throws Exception{

        UIManager.setLookAndFeel(new FlatMacLightLaf());
        MainFrame.showMainFrame();
    }
}
