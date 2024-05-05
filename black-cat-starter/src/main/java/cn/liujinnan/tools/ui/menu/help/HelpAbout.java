/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ui.menu.help;

import cn.liujinnan.tools.constant.LanguageEnum;
import cn.liujinnan.tools.ui.MainFrame;
import cn.liujinnan.tools.utils.PropertiesUtils;
import cn.liujinnan.tools.utils.markdown.MdToHtmlUtil;
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
public class HelpAbout extends JMenuItem {

    public HelpAbout(){
        init();
    }

    private void init() {
        PropertiesUtils instance = PropertiesUtils.getInstance();
        this.setText(instance.getValue(LanguageEnum.MENU_HELP_ABOUT.getKey()));

        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame jFrame = new JFrame();
                    jFrame.setLocationRelativeTo(null);

                    // todo 启动多线程生成html，缓存
                    JEditorPane editorPane = new JEditorPane();
                    editorPane.setContentType("text/html");
                    String html = MdToHtmlUtil.mdToHtml("/about.md");
                    editorPane.setText(html);
                    editorPane.setEditable(false);
                    editorPane.setBackground(Color.WHITE);

                    jFrame.setSize(300, 500);
                    jFrame.add(editorPane);
                    jFrame.setVisible(true);
                } catch (Exception ex) {
                    log.error("about error", ex);
                }

            }
        });
    }
}
