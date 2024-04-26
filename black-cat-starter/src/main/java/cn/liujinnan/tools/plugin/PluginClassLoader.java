package cn.liujinnan.tools.plugin;

import cn.liujinnan.tools.plugin.domain.PluginJarInfo;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * todo 加载配置
 * 插件类加载器
 * @author liujinnan
 */
public class PluginClassLoader extends URLClassLoader {

    private static final String JAR_SUFFIX = ".jar";

    private PluginClassLoader(String name, URL[] urls, ClassLoader parent) {
        super(name, urls, parent);
    }

    /**
     * 创建类名
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
        return new PluginClassLoader(file.getName(), urls, getSystemClassLoader());
    }

    public PluginJarInfo getPluginJarInfo() {



        return null;
    }
}
