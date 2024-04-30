/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.plugin;

import cn.liujinnan.tools.constant.PropertiesEnum;
import cn.liujinnan.tools.plugin.domain.PluginJarInfo;
import cn.liujinnan.tools.utils.PropertiesUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: jar 插件管理
 * @author: liujinnan
 * @create: 2024-04-29 00:01
 **/
@Slf4j
public class PluginManager {

    /**
     * 单例
     */
    private static final PluginManager PLUGINMANAGER = new PluginManager();

    private final Map<String, PluginClassLoader> pluginClassLoaderMap = Maps.newTreeMap();

    private PluginManager() {
        reloadPlugin();
    }

    /**
     * 加载插件jar
     */
    public void reloadPlugin() {
        pluginClassLoaderMap.clear();
        PropertiesUtils instance = PropertiesUtils.getInstance();
        String pluginPath = instance.getValue(PropertiesEnum.BLACK_CAT_PLUGIN_PATH.getKey());
        if (StringUtils.isBlank(pluginPath)) {
            log.info("Configuration item [{}:{}] is null, skipping loading plugins.",
                    PropertiesEnum.BLACK_CAT_PLUGIN_PATH.getKey(), PropertiesEnum.BLACK_CAT_PLUGIN_PATH.getName());
            return;
        }
        File pluginDirectory = new File(pluginPath);
        if (!pluginDirectory.exists() || !pluginDirectory.isDirectory()){
            log.info("The plugin directory is not exist, skipping loading plugins. path={}", pluginPath);
            return;
        }

        Optional.ofNullable(pluginDirectory.listFiles()).ifPresent((fileArrays)->{
            // 过滤只加载jar文件
            Arrays.stream(fileArrays).filter(e -> e.getName().contains(".jar")).forEach(jarFile -> {
                String jarFilePath = jarFile.getAbsolutePath();
                try {
                    PluginClassLoader classLoader = PluginClassLoader.createPluginClassLoader(jarFilePath);
                    PluginJarInfo pluginJarInfo = classLoader.getPluginJarInfo();
                    pluginClassLoaderMap.put(pluginJarInfo.getJarName(), classLoader);
                    // todo 加载插件
                    log.info("load plugin success. path={}", jarFilePath);
                } catch (Exception e) {
                    log.info("load plugin error. path={}", jarFilePath);
                }
            });
        });

    }

    public List<PluginClassLoader> getPluginClassLoaderList() {
        return new ArrayList<>(pluginClassLoaderMap.values());
    }

    public static PluginManager getInstance() {
        return PLUGINMANAGER;
    }
}
