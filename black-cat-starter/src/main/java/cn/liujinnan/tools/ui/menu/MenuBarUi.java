package cn.liujinnan.tools.ui.menu;

import cn.liujinnan.tools.constant.LanguageEnum;
import cn.liujinnan.tools.utils.PropertiesUtils;

import javax.swing.*;

/**
 * @description:
 * @author: ljn
 * @create: 2024-05-02 18:13
 **/
public class MenuBarUi extends JMenuBar {

    public MenuBarUi(){
        init();
    }

    private void init(){
        PropertiesUtils instance = PropertiesUtils.getInstance();

        JMenu file = new JMenu(instance.getValue(LanguageEnum.MENU_FILE.getKey()));
        this.add(file);

        JMenu view = new JMenu(instance.getValue(LanguageEnum.MENU_VIEW.getKey()));
        this.add(view);

        JMenu help = new JMenu(instance.getValue(LanguageEnum.MENU_HELP.getKey()));
        this.add(help);
    }
}
