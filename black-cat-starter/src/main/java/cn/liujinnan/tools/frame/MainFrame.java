/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.frame;

import javax.swing.*;
import java.awt.*;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-04-25 16:51
 */
public class MainFrame {

    private static final int MAINFRAME_WIDTH = 600;

    private static final int MAINFRAME_HEIGHT = 600;

    /**
     * 显示主窗口
     */
    public static void showMainFrame(){
        JFrame jf = new JFrame("Black-Cat");
        // 设置窗口大小
        jf.setSize(MAINFRAME_WIDTH, MAINFRAME_HEIGHT);
        // 把窗口位置设置到屏幕中心
        jf.setLocationRelativeTo(null);
        // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.getImage(MainFrame.class.getResource("/logo.png"));
        jf.setIconImage(image);

        // 创建选项卡
        JTabbedPane tabbedPane = new JTabbedPane();
        jf.setContentPane(tabbedPane);
        tabbedPane.addTab("aa", new JPanel());
//        Map<String, Tab> map = TabAnnotationUtil.getTabMap(MainFrame.class);
//        for (Map.Entry<String, Tab> entry : map.entrySet()) {
//            try {
//                tabbedPane.addTab(entry.getKey(), entry.getValue().getTab());
//            } catch (Exception e) {
//                tabbedPane.addTab("Error:"+entry.getKey(), new ErrorTab(e).getTab());
//            }
//        }

        jf.setVisible(true);

    }
}
