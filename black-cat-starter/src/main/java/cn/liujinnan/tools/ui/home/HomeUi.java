package cn.liujinnan.tools.ui.home;

import cn.liujinnan.tools.constant.LanguageEnum;
import cn.liujinnan.tools.plugin.PluginClassLoader;
import cn.liujinnan.tools.plugin.PluginManager;
import cn.liujinnan.tools.plugin.domain.PluginItem;
import cn.liujinnan.tools.plugin.domain.PluginJarInfo;
import cn.liujinnan.tools.ui.MainFrame;
import cn.liujinnan.tools.utils.ColorUtils;
import cn.liujinnan.tools.utils.PropertiesUtils;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;

/**
 * @description:
 * @author: ljn
 * @create: 2024-05-02 21:43
 **/
@Slf4j
public class HomeUi extends JPanel {

    private JTabbedPane jTabbedPane;

    public HomeUi() {
        init();
    }

    private void init() {
        this.setLayout(new GridLayout(1, 2));
        UIManager.put("TabbedPane.tabType", "underlined");
        UIManager.put("TabbedPane.showTabSeparators", true);
        UIManager.put("TabbedPane.hasFullBorder", true);

        jTabbedPane = new JTabbedPane();
        jTabbedPane.setTabPlacement(JTabbedPane.LEFT);
        jTabbedPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.add(jTabbedPane);

        showPlugin();
    }

    private void showPlugin() {
        try {
            PluginManager pluginManager = PluginManager.getInstance();
            PropertiesUtils instance = PropertiesUtils.getInstance();
            //第一行为标题行，不可选中
            jTabbedPane.addTab(instance.getValue(LanguageEnum.HOME_PLUGIN.getKey()), new JLabel());
            jTabbedPane.setEnabledAt(0, false);

            List<PluginClassLoader> pluginClassLoaderList = pluginManager.getPluginClassLoaderList();
            if (Objects.isNull(pluginClassLoaderList) || pluginClassLoaderList.isEmpty()) {
                jTabbedPane.addTab(instance.getValue(LanguageEnum.HOME_PLUGIN_EMPTY.getKey()), new JLabel());
            }
            pluginClassLoaderList.forEach(pluginClassLoader -> {
                // 样式
                UIManager.put("TabbedPane.tabType", "card");
                UIManager.put("JTabbedPane.tabClosable", true);
                UIManager.put("TabbedPane.tabLayoutPolicy", "scroll");

                PluginJarInfo pluginJarInfo = pluginClassLoader.getPluginJarInfo();
                JPanel itemPanel = new JPanel(new BorderLayout());
                jTabbedPane.addTab(pluginJarInfo.getJarName(), itemPanel);

                JTabbedPane itemPane = new JTabbedPane();
                itemPane.setTabPlacement(JTabbedPane.TOP);
                // tab 关闭功能
                itemPane.putClientProperty("JTabbedPane.tabClosable", true);
                itemPane.putClientProperty("JTabbedPane.tabCloseCallback", (IntConsumer) itemPane::remove);
                // 设置工具栏
                HomeToolBar toolBar = new HomeToolBar(pluginJarInfo, itemPane);
                itemPanel.add(toolBar, BorderLayout.NORTH);
                // 设置标签页
                itemPanel.add(itemPane, BorderLayout.CENTER);
                try {
                    List<PluginItem> pluginItemList = pluginJarInfo.getPluginItemList();
                    pluginItemList.forEach(pluginItem -> {
                        itemPane.addTab(pluginItem.getComponentName(), pluginItem.getIcon(), pluginItem.getJComponent());
                        log.info("load : jarName={} className={}", pluginJarInfo.getJarName(), pluginItem.getClassName());
                    });
                } catch (Exception e) {
                    log.error("load plugin item error. jar={}", pluginJarInfo.getJarName(), e);
                }
            });
            //默认选中第二行
            jTabbedPane.setSelectedIndex(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Icon getImageIcon() {
        FlatSVGIcon homeIcon = new FlatSVGIcon("img/home.svg", 35, 35);
        String homeImg = Objects.requireNonNull(MainFrame.class.getResource("/img/home.png")).getPath();
//        return new ImageIcon(homeImg);
        return homeIcon;
    }
}
