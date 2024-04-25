/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools;

import cn.liujinnan.tools.frame.MainFrame;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

/**
 * @description: 启动类
 * @author: liujinnan
 * @create: 2024-04-26 01:28
 **/
public class Application {

    public static void main(String[] args) throws Exception{
        UIManager.setLookAndFeel(new FlatMacLightLaf());
        MainFrame.showMainFrame();
    }
}
