/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ui.favorites;

import cn.liujinnan.tools.constant.PropertiesEnum;
import cn.liujinnan.tools.ui.component.ComponentIcon;
import cn.liujinnan.tools.utils.PropertiesUtils;

import javax.swing.*;
import java.awt.*;

/**
 * 页面左侧收藏tab
 *
 * @author ljn
 * @version 1.0
 * @date 2024-05-24 10:17
 */
public class FavoritesUi extends JPanel implements ComponentIcon {

    public FavoritesUi() {
        init();
    }

    private void init() {
        JTabbedPane favoritesTabbedPane = new JTabbedPane();
        favoritesTabbedPane.setTabPlacement(JTabbedPane.TOP);
        favoritesTabbedPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.add(favoritesTabbedPane);
    }

    /**
     *
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
