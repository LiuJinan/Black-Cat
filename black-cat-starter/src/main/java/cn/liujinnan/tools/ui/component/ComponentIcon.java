/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ui.component;

import javax.swing.*;

/**
 * @description:
 * @author: liujinnan
 * @create: 2024-05-25 23:05
 **/
public interface ComponentIcon {


    /**
     * 获取组件图标。组件聚焦与未聚焦返回不同图标
     * @param selected selected激活或选中
     * @return
     */
    Icon getIcon(boolean selected);

    default Icon getIconNotSelected(){
        return getIcon(false);
    }

}
