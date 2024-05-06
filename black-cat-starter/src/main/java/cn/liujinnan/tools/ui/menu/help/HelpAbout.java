/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ui.menu.help;

import cn.liujinnan.tools.constant.LanguageEnum;
import cn.liujinnan.tools.utils.PropertiesUtils;
import cn.liujinnan.tools.utils.markdown.MdToHtmlUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @description: 菜单栏。 帮助->关于
 * @author: liujinnan
 * @create: 2024-05-05 21:03
 **/
@Slf4j
@Setter
public class HelpAbout extends JMenuItem {

    public HelpAbout() {
        init();
    }

    private void init() {
        PropertiesUtils instance = PropertiesUtils.getInstance();
        this.setText(instance.getValue(LanguageEnum.MENU_HELP_ABOUT.getKey()));

        JFrame jFrame = getAboutFrame();
        // 点击显示
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(true);
            }
        });
    }

    private JFrame getAboutFrame() {
        JFrame jFrame = new JFrame();
        // 异步初始化窗体
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    // 弹出框位于主窗体中间
                    jFrame.setLocationRelativeTo(null);
                    jFrame.setSize(300, 400);
                    // 中间展示
                    Point location = jFrame.getLocation();
                    jFrame.setLocation((int)location.getX()-(jFrame.getWidth()/2), (int)location.getY()-(jFrame.getHeight()/2));

                    JEditorPane editorPane = new JEditorPane();
                    editorPane.setContentType("text/html");
                    editorPane.setText(MdToHtmlUtil.mdToHtml("/about.md"));
                    editorPane.setEditable(false);
                    jFrame.add(editorPane);
                }
            });
        } catch (Exception e) {

        }
        return jFrame;
    }
}
