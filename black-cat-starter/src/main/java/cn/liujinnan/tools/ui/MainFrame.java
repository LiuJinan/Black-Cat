/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ui;

import cn.liujinnan.tools.constant.PropertiesEnum;
import cn.liujinnan.tools.ui.home.HomeUi;
import cn.liujinnan.tools.ui.menu.MenuBarUi;
import cn.liujinnan.tools.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-04-25 16:51
 */
@Slf4j
public class MainFrame {

    private static final JFrame JF = new JFrame("Black-Cat");

    public static JFrame getMainFrame(){
        return JF;
    }

    /**
     * 显示主窗口
     */
    public static void showMainFrame(){

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

        // 左侧菜单选项卡
        UIManager.put("TabbedPane.tabType", "card");
        UIManager.put("TabbedPane.hasFullBorder", true);
        JTabbedPane leftPane = new JTabbedPane();
        leftPane.setTabPlacement(JTabbedPane.LEFT);
        JF.setContentPane(leftPane);
        // 首行为空
        leftPane.addTab("", new JLabel());
        leftPane.setEnabledAt(0, false);
        // 主页
        HomeUi homeUi = new HomeUi();
        leftPane.addTab("",homeUi.getImageIcon(), homeUi);
        leftPane.setSelectedIndex(1);

        //收藏
//        JPanel favorites = new JPanel(new GridLayout(1, 2));
//        String favoritesImg = MainFrame.class.getResource("/img/favorites.png").getPath();
//        leftPane.addTab("",new ImageIcon(favoritesImg), favorites);
//        JTabbedPane favoritesTabbedPane = new JTabbedPane();
//        favoritesTabbedPane.setTabPlacement(JTabbedPane.TOP);
//        favoritesTabbedPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
//        favorites.add(favoritesTabbedPane);
        JF.setVisible(true);
    }


}
