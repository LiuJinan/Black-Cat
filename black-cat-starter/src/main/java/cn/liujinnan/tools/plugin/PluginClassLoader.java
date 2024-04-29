package cn.liujinnan.tools.plugin;

import cn.liujinnan.tools.plugin.domain.PluginJarInfo;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * todo 加载配置
 * 插件类加载器
 * @author liujinnan
 */
public class PluginClassLoader extends URLClassLoader {

    private static final String JAR_SUFFIX = ".jar";

    private String jarFilePath;

    private PluginClassLoader(String name, URL[] urls, ClassLoader parent, String jarFilePath) {
        super(name, urls, parent);
        this.jarFilePath = jarFilePath;
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
        return new PluginClassLoader(file.getName(), urls, getSystemClassLoader(), jarFilePath);
    }

    public PluginJarInfo getPluginJarInfo() throws Exception{

        PluginJarInfo pluginJarInfo = new PluginJarInfo();
        Properties properties = new Properties();
        try(JarFile jarFile = new JarFile(jarFilePath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                System.out.println(jarEntry.getName());
                if (StringUtils.equals(jarEntry.getName(), "plugin.properties")){
                    InputStream inputStream = jarFile.getInputStream(jarEntry);
                    properties.load(inputStream);
                }
            }
        }

        properties.keySet().forEach(key -> {
            System.out.println(key +" : " + properties.get(key));
        });

        return null;
    }
}
