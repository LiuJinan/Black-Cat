/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.ext.plugin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PluginComponent {

    String name() default "pluginName";
}
