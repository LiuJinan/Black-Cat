/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.cache.domain;

import lombok.Data;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-05-23 18:50
 */
@Data
public class FavoriteItem {

    /**
     * jar文件名，不包含路径
     */
    private String jarName;

    /**
     * 全限定类名, 包含包路径。例：cn.liujinnan.tools.plugin.domain。PluginItem
     */
    private String className;
}
