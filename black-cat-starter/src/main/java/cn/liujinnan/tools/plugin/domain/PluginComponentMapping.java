/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.plugin.domain;

import lombok.Data;

import javax.swing.*;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-06-01 15:01
 */
@Data
public class PluginComponentMapping {

    private String jarName;

    private String className;

    private JComponent component;
}
