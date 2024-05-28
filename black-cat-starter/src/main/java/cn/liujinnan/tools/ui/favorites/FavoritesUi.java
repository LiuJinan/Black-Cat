/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ui.favorites;

import cn.liujinnan.tools.cache.FavoritesCache;
import cn.liujinnan.tools.constant.PropertiesEnum;
import cn.liujinnan.tools.plugin.PluginClassLoader;
import cn.liujinnan.tools.plugin.PluginManager;
import cn.liujinnan.tools.plugin.domain.PluginItem;
import cn.liujinnan.tools.ui.component.ComponentIcon;
import cn.liujinnan.tools.utils.PropertiesUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 页面左侧收藏tab
 *
 * @author ljn
 * @version 1.0
 * @date 2024-05-24 10:17
 */
public class FavoritesUi extends JPanel implements ComponentIcon {

    private JTabbedPane favoritesTabbedPane;

    public FavoritesUi() {
        init();
    }

    private void init() {
        this.setLayout(new GridLayout(1, 2));
        UIManager.put("TabbedPane.tabType", "underlined");
        UIManager.put("TabbedPane.underlineColor", UIManager.get("TabbedPane.background"));
        UIManager.put("TabbedPane.inactiveUnderlineColor", UIManager.get("TabbedPane.focusColor"));

        favoritesTabbedPane = new JTabbedPane();
        favoritesTabbedPane.setTabPlacement(JTabbedPane.LEFT);
        favoritesTabbedPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        favoritesTabbedPane.putClientProperty("JTabbedPane.minimumTabWidth", 180);
        this.add(favoritesTabbedPane);

        reload();
    }

    public void reload() {
        favoritesTabbedPane.removeAll();
        List<PluginClassLoader> pluginClassLoaderList = PluginManager.getInstance().getPluginClassLoaderList();
        Map<String, Map<String, PluginItem>> collect = pluginClassLoaderList.stream()
                .collect(Collectors.toMap(
                        e -> e.getPluginJarInfo().getJarName(),
                        e -> e.getPluginJarInfo().getPluginItemList().stream().collect(Collectors.toMap(PluginItem::getClassName, i -> i))));
        FavoritesCache.getAll().forEach(e -> {

            Map<String, PluginItem> pluginItemMap = collect.get(e.getJarName());
            if (Objects.isNull(pluginItemMap) || pluginItemMap.isEmpty()) {
                return;
            }
            PluginItem pluginItem = pluginItemMap.get(e.getClassName());
            if (Objects.isNull(pluginItem)) {
                return;
            }
            favoritesTabbedPane.addTab(pluginItem.getComponentName(), pluginItem.getJComponent());
        });
    }

    /**
     * @param selected selected=true 被选中
     * @return
     */
    @Override
    public Icon getIcon(boolean selected) {
        if (selected) {
            return (Icon) PropertiesUtils.getInstance().getObject(PropertiesEnum.SVG_FAVORITES_FILL.getKey());
        }
        return (Icon) PropertiesUtils.getInstance().getObject(PropertiesEnum.SVG_FAVORITES.getKey());
    }
}
