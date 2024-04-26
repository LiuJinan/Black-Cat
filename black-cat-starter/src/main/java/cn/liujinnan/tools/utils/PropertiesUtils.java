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
 *   安装目录配置 > 项目内配置文件
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
     * 配置文件夹名称
     */
    private static final String CONFIG_FOLDER_NAME = "config";

    /**
     * 语言配置文件夹。 config/language/zh_cn.properties
     */
    private static final String LANGUAGE_FOLDER_NAME = "language";

    /**
     * 单例饿汉
     */
    private static final PropertiesUtils TETRIS_CACHE_CONFIG = new PropertiesUtils();

    /**
     * 存放加载的配置
     */
    private final Properties PROPERTIES = new Properties();


    private PropertiesUtils() {
        init();
    }

    private void init(){
        log.info("load all config start..........");
        // 配种文件目录
        try {
            // 加载主配置
            loadProperties();
            // 加载语言配置
            loadLanguageProperties();

            log.info("load all config done..........");
        } catch (Exception e) {
            log.error("加载配置失败", e);
        }
    }

    public static PropertiesUtils getInstance(){
        return TETRIS_CACHE_CONFIG;
    }

    /**
     * 加载主配置
     * @throws IOException
     */
    private void loadProperties() throws IOException{
        // 加载项目中的默认配置文件
        URL configUrl = Objects.requireNonNull(this.getClass().getResource("/" + PROPERTIES_FILE));
        PROPERTIES.load(configUrl.openStream());
        log.info("load config file [{}] done ", configUrl.getPath());

        if (!configUrl.getPath().contains(JAR_SUFFIX)){
            return;
        }

        // 外置配置文件绝对路径
        String outConfigFilePath = getJarConfigFolder() + PROPERTIES_FILE;
        File file = new File(outConfigFilePath);
        if (!file.exists()){
            return;
        }
        PROPERTIES.load(new FileReader(file));
        log.info("load config file [{}] done ", outConfigFilePath);
    }

    /**
     * 加载语言配置
     */
    private void loadLanguageProperties() throws IOException{

        // 语言文件名称
        String languageFileName = PROPERTIES.getProperty(PropertiesEnum.LANGUAGE.getKey(), "zh_cn") + PROPERTIES_SUFFIX;
        // 加载项目内置语言配置文件
        URL languageUrl = Objects.requireNonNull(this.getClass().getResource("/" + LANGUAGE_FOLDER_NAME + "/" + languageFileName));
        PROPERTIES.load(languageUrl.openStream());
        log.info("load config file {{}} done ", languageUrl.getPath());

        if (!languageUrl.getPath().contains(JAR_SUFFIX)){
            return;
        }

        // 加载外置的配置文件
        String outLanguageConfigFilePath = getJarConfigFolder()  + LANGUAGE_FOLDER_NAME + File.separator + languageFileName;
        File file = new File(outLanguageConfigFilePath);
        if (!file.exists()){
            return;
        }
        PROPERTIES.load(new FileReader(file));
        log.info("load config file [{}] done ", outLanguageConfigFilePath);
    }

    /**
     * jar方式运行时，获取配置文件目录
     * @return
     */
    public String getJarConfigFolder(){

        // /绝对路径/xxx.jar
        String jarPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        if (StringUtils.isBlank(jarPath) || !jarPath.contains(JAR_SUFFIX)){
            return CONFIG_FOLDER_NAME + File.separator;
        }
        return new File(jarPath).getParent() + File.separator + CONFIG_FOLDER_NAME + File.separator;
    }

    public String getValue(String propertiesKey) {
        return PROPERTIES.getProperty(propertiesKey);
    }

    public Integer getIntValue(String propertiesKey) {
        return Integer.valueOf(PROPERTIES.getProperty(propertiesKey));
    }
}
