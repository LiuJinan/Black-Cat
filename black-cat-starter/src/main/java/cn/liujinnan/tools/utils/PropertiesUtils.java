package cn.liujinnan.tools.utils;
/*
 * Copyright(c) 2022 by liujinnan.cn All rights Reserved.
 */

import cn.liujinnan.tools.constant.PropertiesEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    private static final String PROPERTIES_FILE = "/application.properties";

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
        String outPath = PropertiesUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        // outPath = 打包的jar所在目录
        outPath = outPath.substring(0, outPath.lastIndexOf("/"));
        try {
            // 加载主配置
            loadProperties(outPath);
            // 加载语言配置
            loadLanguageProperties(outPath,PROPERTIES.getProperty(PropertiesEnum.LANGUAGE.getKey()));
            if (log.isDebugEnabled()) {
                PROPERTIES.stringPropertyNames().forEach(key ->{
                    log.debug("config: {}={}", key, PROPERTIES.getProperty(key));
                });
            }
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
     * @param outPath 项目根目录，用于获取外置的配置文件
     * @throws IOException
     */
    private void loadProperties(String outPath) throws IOException{
        // 加载项目中的默认配置文件
        PROPERTIES.load(PropertiesUtils.class.getResourceAsStream(PROPERTIES_FILE));
        log.info("load config file {} done ", PROPERTIES_FILE);

        // 项目外的配置文件 jar所在目录/config/name.properties
        String outConfigFile = outPath+"/config"+ PROPERTIES_FILE;
        File file = new File(outConfigFile);
        if (file.exists()){
            try(FileInputStream fileInputStream = new FileInputStream(file);) {
                PROPERTIES.load(fileInputStream);
            }
            log.info("load config file {} done", outConfigFile);
        }
    }

    /**
     * 加载语言配置
     * @param outPath 项目根目录，用于获取外置的配置文件
     * @param language
     */
    private void loadLanguageProperties(String outPath, String language) throws IOException{
        // 语言文件名称
        String languageFileName = language + ".properties";

        // 加载项目内置语言配置文件
        PROPERTIES.load(PropertiesUtils.class.getResourceAsStream(languageFileName));
        log.info("load config file {} done ", languageFileName);

        // 加载外置的配置文件
        String outLanguageConfigFile = outPath + "/config/" + languageFileName;
        File file = new File(outLanguageConfigFile);
        if (file.exists()){
            try(FileInputStream fileInputStream = new FileInputStream(file);) {
                PROPERTIES.load(fileInputStream);
            }
            log.info("load config file {} done", outLanguageConfigFile);
        }
    }

    public String getValue(String propertiesKey) {
        return PROPERTIES.getProperty(propertiesKey);
    }

    public Integer getIntValue(String propertiesKey) {
        return Integer.valueOf(PROPERTIES.getProperty(propertiesKey));
    }
}
