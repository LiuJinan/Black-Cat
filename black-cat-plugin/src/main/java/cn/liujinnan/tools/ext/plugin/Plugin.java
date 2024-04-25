/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ext.plugin;

import javax.swing.*;

public interface Plugin {

    /**
     * 获取组件
     * @return
     */
    JComponent getJComponent();

    /**
     * 排序。值越大越靠前
     * @return
     */
    default int order(){
        return 0;
    }
}
