package cn.liujinnan.tools.plugin;

import cn.liujinnan.tools.ext.plugin.Plugin;
import cn.liujinnan.tools.ext.plugin.annotation.PluginComponent;
import cn.liujinnan.tools.ext.plugin.constant.PluginPropertiesEnum;
import cn.liujinnan.tools.plugin.domain.PluginItem;
import cn.liujinnan.tools.plugin.domain.PluginJarInfo;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 插件类加载器
 *
 * @author liujinnan
 */
@Getter
@Slf4j
public class PluginClassLoader extends URLClassLoader {

    private static final String JAR_SUFFIX = ".jar";

    private static final String CLASS_SUFFIX = ".class";

    private PluginJarInfo pluginJarInfo;

    private PluginClassLoader(String name, URL[] urls, ClassLoader parent, String jarFilePath) throws Exception {
        super(name, urls, parent);
        reloadPluginJarInfo(jarFilePath);
    }

    /**
     * 创建类名
     *
     * @param jarFilePath jar包文件路径，绝对路径。 绝对路径/xxx.jar
     * @return
     */
    public static PluginClassLoader createPluginClassLoader(String jarFilePath) throws Exception {
        URL[] urls = null;

        if (StringUtils.isBlank(jarFilePath) || !StringUtils.endsWith(jarFilePath, JAR_SUFFIX)) {
            throw new IllegalArgumentException("params jarFilePath illegal");
        }
        File file = new File(jarFilePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        urls = new URL[]{file.toURI().toURL()};
        return new PluginClassLoader(file.getName(), urls, getSystemClassLoader(), jarFilePath);
    }

    /**
     * 加载插件信息
     *
     * @param jarFilePath
     * @return
     */
    private void reloadPluginJarInfo(String jarFilePath) throws Exception {

        pluginJarInfo = new PluginJarInfo();
        pluginJarInfo.setJarPath(jarFilePath);
        List<PluginItem> pluginItemList = Lists.newArrayList();
        pluginJarInfo.setPluginItemList(pluginItemList);
        try (JarFile jarFile = new JarFile(jarFilePath)) {
            pluginJarInfo.setJarName(new File(jarFile.getName()).getName());
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (StringUtils.equals(jarEntry.getName(), "plugin.properties")) {
                    loadPluginProperties(jarFile.getInputStream(jarEntry), pluginJarInfo);
                    continue;
                }
                Optional.ofNullable(checkAndGetPluginClass(jarEntry.getName(), jarFile))
                        .ifPresent(pluginItemList::add);
            }
            if (pluginItemList.isEmpty()) {
                throw new IllegalAccessException();
            }
        } catch (IOException e) {
            log.error("Failed to load plugin. jarFilePath={}", jarFilePath, e);
        }
    }

    private void loadPluginProperties(InputStream inputStream, PluginJarInfo pluginJarInfo) {
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            // 插件配置
            String author = properties.getProperty(PluginPropertiesEnum.PLUGIN_AUTHOR.getKey(), PluginPropertiesEnum.PLUGIN_AUTHOR.getDefaultValue());
            pluginJarInfo.setAuthor(author);
            String version = properties.getProperty(PluginPropertiesEnum.PLUGIN_VERSION.getKey(), PluginPropertiesEnum.PLUGIN_VERSION.getDefaultValue());
            pluginJarInfo.setVersion(version);
            log.info("load plugin config success. pluginName={}", pluginJarInfo.getJarName());
        } catch (Exception e) {
            log.error("load plugin config error. pluginName={}", pluginJarInfo.getJarName(), e);
        }
    }

    /**
     * 检查当前文件为plugin class则返回
     *
     * @param jarEntryName
     * @return
     */
    private PluginItem checkAndGetPluginClass(String jarEntryName, JarFile jarFile) {
        if (StringUtils.isBlank(jarEntryName) || !jarEntryName.contains(CLASS_SUFFIX)) {
            // 非class文件跳过
            return null;
        }
        try {
            // jarEntryName 格式 cn/liujinnan/tools/Test.class
            String className = jarEntryName.replaceAll("/", ".").replace(CLASS_SUFFIX, "");
            Class<?> aClass = this.loadClass(className);
            PluginComponent pluginComponent = aClass.getAnnotation(PluginComponent.class);
            if (Objects.nonNull(pluginComponent)) {
                Object obj = aClass.getDeclaredConstructor().newInstance();
                if (!(obj instanceof Plugin)) {
                    return null;
                }
                PluginItem pluginItem = new PluginItem();
                pluginItem.setClassName(className);
                pluginItem.setSimpleClassName(aClass.getSimpleName());
                pluginItem.setComponentName(pluginComponent.name());
                // 图标
                pluginItem.setIcon(getImageIcon(pluginComponent.icon(), jarFile));
                Plugin plugin = (Plugin) obj;
                pluginItem.setPlugin(plugin);
                pluginItem.setJComponent(plugin.getJComponent());
                return pluginItem;
            }
        } catch (Exception e) {
            log.error("load plugin class error. className={}", jarEntryName, e);
        }
        return null;
    }

    private ImageIcon getImageIcon(String iconName, JarFile jarFile) {
        if (StringUtils.isBlank(iconName)) {
            return null;
        }
        try {
            JarEntry jarEntry = jarFile.getJarEntry(iconName);
            InputStream inputStream = jarFile.getInputStream(jarEntry);
            ImageIcon imageIcon = new ImageIcon(inputStream.readAllBytes());
            // 重置图标长宽
            Image image = imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } catch (Exception e) {
            log.error("Failed to get icon. jarName={}, iconName={}", jarFile.getName(), iconName);
        }
        return null;
    }
}
