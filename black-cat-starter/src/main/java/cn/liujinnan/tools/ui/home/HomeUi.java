package cn.liujinnan.tools.ui.home;

import cn.liujinnan.tools.constant.LanguageEnum;
import cn.liujinnan.tools.constant.PropertiesEnum;
import cn.liujinnan.tools.plugin.PluginClassLoader;
import cn.liujinnan.tools.plugin.PluginManager;
import cn.liujinnan.tools.plugin.domain.PluginItem;
import cn.liujinnan.tools.plugin.domain.PluginJarInfo;
import cn.liujinnan.tools.utils.PropertiesUtils;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;

/**
 * @description: 主页
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
            jTabbedPane.setFont(new Font(null, Font.PLAIN, 15));
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
                // 插件选项卡设置为圆角
                UIManager.put("TabbedPane.cardTabArc", 20);
//                UIManager.put("TabbedPane.underlineColor", Color.decode("#3574F0"));

                PluginJarInfo pluginJarInfo = pluginClassLoader.getPluginJarInfo();
                JPanel itemPanel = new JPanel(new BorderLayout());
                jTabbedPane.addTab(pluginJarInfo.getJarName(), itemPanel);

                JTabbedPane itemPane = new JTabbedPane();
                itemPane.setTabPlacement(JTabbedPane.TOP);
                // tab 关闭功能
                itemPane.putClientProperty("JTabbedPane.tabClosable", true);
                itemPane.putClientProperty("JTabbedPane.tabCloseCallback", (IntConsumer) itemPane::remove);
                itemPane.putClientProperty("JTabbedPane.minimumTabWidth", 100);
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
                    log.error("Failed to load plug-in item. jar={}", pluginJarInfo.getJarName(), e);
                }
            });
            //默认选中第二行
            jTabbedPane.setSelectedIndex(1);
        } catch (Exception e) {
           log.error("Failed to display plugin list.", e);
        }
    }

    public Icon getImageIcon() {
        return (Icon)PropertiesUtils.getInstance().getObject(PropertiesEnum.SVG_HOME.getKey());
    }
}
