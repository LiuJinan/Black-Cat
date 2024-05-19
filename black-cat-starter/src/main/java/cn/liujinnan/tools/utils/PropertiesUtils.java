package cn.liujinnan.tools.utils;
/*
 * Copyright(c) 2022 by liujinnan.cn All rights Reserved.
 */

import cn.liujinnan.tools.constant.PropertiesEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

/**
 * 启动时加载所有配置
 * 优先加载优先级低的配置，配置key相同时，优先级高的覆盖低的
 * 优先级：
 * 安装目录配置 > 项目内配置文件
 *
 * @author LiuJinan
 */
@Slf4j
public class PropertiesUtils {

    /**
     * 配置文件后缀
     */
    private static final String PROPERTIES_SUFFIX = ".properties";

    /**
     * 配种文件名称
     */
    private static final String PROPERTIES_FILE = "application" + PROPERTIES_SUFFIX;

    /**
     * jar文件后缀
     */
    private static final String JAR_SUFFIX = ".jar";

    /**
     * 单例饿汉
     */
    private static final PropertiesUtils TETRIS_CACHE_CONFIG = new PropertiesUtils();

    /**
     * 存放加载的配置
     */
    private final Properties PROPERTIES = new Properties();


    private PropertiesUtils() {
        reload();
    }

    /**
     * 加载配置项
     */
    private void reload() {
        log.info("load all config start..........");
        // 配种文件目录
        try {
            // 设置默认配置
            initDefault();
            // 加载主配置
            loadProperties();
            // 加载语言配置
            loadLanguageProperties();

            log.info("load all config done..........");
        } catch (Exception e) {
            log.error("加载配置失败", e);
        }
    }

    private void initDefault() {
        // 1.初始化项目根目录
        // 非jar方式启动，path=/绝对路径/black-cat-starter/target/classes/
        // jar方式启动，path=/绝对路径/xxx.jar
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        if (path.contains(JAR_SUFFIX)) {
            // 以jar方式启动，path = /绝对路径/
            path = new File(path).getParent() + File.separator;
        }
        PROPERTIES.setProperty(PropertiesEnum.BLACK_CAT_HOME_PATH.getKey(), path);

        // 2.初始化配置文件目录
        PROPERTIES.setProperty(PropertiesEnum.BLACK_CAT_CONFIG_PATH.getKey(), path + "config" + File.separator);

        // 3.初始化默认插件目录位置
        PROPERTIES.setProperty(PropertiesEnum.BLACK_CAT_PLUGIN_PATH.getKey(), path + "plugin" + File.separator);

        // 4. 语言文件配置目录
        PROPERTIES.setProperty(PropertiesEnum.BLACK_CAT_LANGUAGE_PATH.getKey(), path + "language" + File.separator);

    }

    /**
     * 加载主配置
     *
     * @throws IOException
     */
    private void loadProperties() throws IOException {
        // 加载项目中的默认配置文件
        URL configUrl = Objects.requireNonNull(this.getClass().getResource("/" + PROPERTIES_FILE));
        PROPERTIES.load(configUrl.openStream());
        log.info("load config file [{}] done ", configUrl.getPath());

        if (!configUrl.getPath().contains(JAR_SUFFIX)) {
            return;
        }

        // 外置配置文件绝对路径
        String outConfigFilePath = PROPERTIES.getProperty(PropertiesEnum.BLACK_CAT_CONFIG_PATH.getKey()) + PROPERTIES_FILE;
        File file = new File(outConfigFilePath);
        if (!file.exists()) {
            return;
        }
        PROPERTIES.load(new FileReader(file));
        log.info("load config file [{}] done ", outConfigFilePath);
    }

    /**
     * 加载语言配置
     */
    private void loadLanguageProperties() throws IOException {

        // 加载项目内置语言配置文件
        String languageFileName = PROPERTIES.getProperty(PropertiesEnum.LANGUAGE.getKey(), "zh_cn") + PROPERTIES_SUFFIX;
        URL languageUrl = Objects.requireNonNull(this.getClass().getResource("/language/" + languageFileName));
        PROPERTIES.load(languageUrl.openStream());
        log.info("load config file {{}} done ", languageUrl.getPath());

        if (!languageUrl.getPath().contains(JAR_SUFFIX)) {
            return;
        }

        // 加载外置的配置文件
        String languagePath = PROPERTIES.getProperty(PropertiesEnum.BLACK_CAT_LANGUAGE_PATH.getKey());
        File file = new File(languagePath + languageFileName);
        if (!file.exists()) {
            return;
        }
        PROPERTIES.load(new FileReader(file));
        log.info("load config file [{}] done ", file.getAbsolutePath());
    }

    public static PropertiesUtils getInstance() {
        return TETRIS_CACHE_CONFIG;
    }

    public String getValue(String propertiesKey) {
        return PROPERTIES.getProperty(propertiesKey);
    }

    public Integer getIntValue(String propertiesKey) {
        return Integer.valueOf(PROPERTIES.getProperty(propertiesKey));
    }

    public Object getObject(String propertiesKey){
        return PROPERTIES.get(propertiesKey);
    }


    public void put(String propertiesKey, Object val) {
        PROPERTIES.put(propertiesKey, val);
    }
}
