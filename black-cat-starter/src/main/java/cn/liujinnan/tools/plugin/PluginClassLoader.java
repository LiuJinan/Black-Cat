package cn.liujinnan.tools.plugin;

import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader {
    public PluginClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }
}
