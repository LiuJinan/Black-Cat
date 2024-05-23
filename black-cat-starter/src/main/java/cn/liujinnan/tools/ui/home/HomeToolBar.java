package cn.liujinnan.tools.ui.home;

import cn.liujinnan.tools.constant.LanguageEnum;
import cn.liujinnan.tools.plugin.domain.PluginItem;
import cn.liujinnan.tools.plugin.domain.PluginJarInfo;
import cn.liujinnan.tools.cache.FavoritesCache;
import cn.liujinnan.tools.utils.ColorUtils;
import cn.liujinnan.tools.utils.PropertiesUtils;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description: home页面工具栏
 * @author: ljn
 * @create: 2024-05-04 12:14
 **/
public class HomeToolBar extends JPanel {

    private static final int TOOL_BUTTON_WIDTH_HEIGHT = 20;

    private final JToolBar jToolBar = new JToolBar();

    /**
     * 当前工具栏对应的jar插件
     */
    private final PluginJarInfo pluginJarInfo;
    /**
     * jar插件对应的plugin标签
     */
    private final JTabbedPane itemPane;

    public HomeToolBar(PluginJarInfo pluginJarInfo, JTabbedPane itemPane) {
        this.pluginJarInfo = pluginJarInfo;
        this.itemPane = itemPane;
        init();
    }

    private void init(){
        this.setLayout(new GridLayout(1,1));
        this.add(jToolBar);
        // 工具栏背景色加深10%
        jToolBar.setBackground(ColorUtils.darkenColor(jToolBar.getBackground(), 0.1));

        // 更新按钮
        initUpdateBtn();

        // 收藏按钮
        initFavoritesBtn();
    }

    private void initUpdateBtn() {
        PropertiesUtils instance = PropertiesUtils.getInstance();
        JButton update = new JButton();
        FlatSVGIcon updateSvg = new FlatSVGIcon("img/default/toolbar/update.svg", TOOL_BUTTON_WIDTH_HEIGHT, TOOL_BUTTON_WIDTH_HEIGHT);
        update.setIcon(updateSvg);
        update.setToolTipText(instance.getValue(LanguageEnum.HOME_PLUGIN_TOOLBAR_UPDATE_BTN_TIP.getKey()));
        //        update.setIcon(new FlatSearchIcon());
//        update.putClientProperty("JButton.buttonType", "help");
//        UIManager.put("JButton.buttonType", "help");

//        update.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);

        jToolBar.add(update);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                updateSvg.setColorFilter(new FlatSVGIcon.ColorFilter(c -> {
//                    return Color.decode("#f4ea2a");
////                    if (update.isSelected()) {
////                        return Color.decode("#f4ea2a");
////                    } else {
////                        return Color.red;
////                    }
//                }));

                List<PluginItem> pluginItemList = pluginJarInfo.getPluginItemList();
                List<Component> existList = Arrays.asList(itemPane.getComponents());
                pluginItemList.forEach(pluginItem -> {
                    if (!existList.contains(pluginItem.getJComponent())) {
                        itemPane.addTab(pluginItem.getComponentName(), pluginItem.getIcon(), pluginItem.getJComponent());
                    }
                });
            }
        });
    }

    private void initFavoritesBtn() {

        JButton favorites = new JButton();
        FlatSVGIcon favoritesSvg = new FlatSVGIcon("img/default/toolbar/favorites.svg", TOOL_BUTTON_WIDTH_HEIGHT, TOOL_BUTTON_WIDTH_HEIGHT);
        favorites.setIcon(favoritesSvg);
        jToolBar.add(favorites);

        FlatSVGIcon favoritesFillSvg = new FlatSVGIcon("img/default/toolbar/favorites-fill.svg", TOOL_BUTTON_WIDTH_HEIGHT, TOOL_BUTTON_WIDTH_HEIGHT);

        Map<Component, PluginItem> pluginItemMap = pluginJarInfo.getPluginItemList().stream().collect(Collectors.toMap(PluginItem::getJComponent, e -> e));

        // 切换插件，调整显示按钮颜色
        itemPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                Component selectedComponent = itemPane.getSelectedComponent();

                PluginItem pluginItem = pluginItemMap.get(selectedComponent);
                Optional.ofNullable(pluginItem).ifPresent(item -> {
                    boolean exist = FavoritesCache.exist(pluginJarInfo.getJarName(), item.getClassName());
                    if (exist) {
                        favorites.setIcon(favoritesFillSvg);
                    }else {
                        favorites.setIcon(favoritesSvg);
                    }
                });
            }
        });
        // 点击收藏按钮
        favorites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component selectedComponent = itemPane.getSelectedComponent();
                PluginItem pluginItem = pluginItemMap.get(selectedComponent);
                // 收藏
                if (favoritesSvg == favorites.getIcon()) {
                    favorites.setIcon(favoritesFillSvg);
                    FavoritesCache.add(pluginJarInfo.getJarName(), pluginItem.getClassName());
                    return;
                }
                // 移除收藏
                if (favoritesFillSvg == favorites.getIcon()) {
                    favorites.setIcon(favoritesSvg);
                    FavoritesCache.remove(pluginJarInfo.getJarName(), pluginItem.getClassName());
                }
            }
        });
    }
}
