/*
 * copyright(c) 2024 by liujinnan.cn All rights Reserved.
 */

package cn.liujinnan.tools.cache;

import cn.liujinnan.tools.constant.BlackCatConstants;
import cn.liujinnan.tools.cache.domain.FavoriteItem;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Optional;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-05-23 18:48
 */
@Slf4j
public class FavoritesCache {

    private static final List<FavoriteItem> FAVORITE_ITEM_LIST = Lists.newArrayList();

    static {
        loadFavorites();
    }

    private static void loadFavorites() {
        File favoritesJsonFile = new File(BlackCatConstants.FAVORITES_CONFIG_FILE_PATH);
        if (favoritesJsonFile.exists() && favoritesJsonFile.isFile()) {
            try (FileReader fileReader = new FileReader(favoritesJsonFile);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String json = StringUtils.join(bufferedReader.lines().toArray());
                List<FavoriteItem> favoriteItems = JSON.parseArray(json, FavoriteItem.class);
                Optional.ofNullable(favoriteItems).ifPresent(FAVORITE_ITEM_LIST::addAll);
            } catch (Exception e) {
                log.error("Failed to load favorite configuration", e);
            }
        }
    }

    public static void add(String jarName, String className) {
        if (exist(jarName, className)) {
            return;
        }
        FavoriteItem item = new FavoriteItem();
        item.setJarName(jarName);
        item.setClassName(className);
        FAVORITE_ITEM_LIST.add(item);
        saveFile();
    }

    public static void remove(String jarName, String className) {
        List<FavoriteItem> list = FAVORITE_ITEM_LIST.stream()
                .filter(e -> StringUtils.equals(jarName, e.getJarName())
                && StringUtils.equals(className, e.getClassName())).toList();
        if (!list.isEmpty()) {
            FAVORITE_ITEM_LIST.removeAll(list);
            saveFile();
        }
    }

    public static boolean exist(String jarName, String className) {
       return FAVORITE_ITEM_LIST.stream().anyMatch(e -> StringUtils.equals(jarName, e.getJarName())
                && StringUtils.equals(className, e.getClassName()));
    }

    private static void saveFile() {

        // 写入文件
        File favoritesJsonFile = new File(BlackCatConstants.FAVORITES_CONFIG_FILE_PATH);
        if (!favoritesJsonFile.exists()) {
            File parentFile = favoritesJsonFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                favoritesJsonFile.createNewFile();
            } catch (Exception e) {

            }

        }
        try (FileWriter fileWriter = new FileWriter(favoritesJsonFile)) {
            fileWriter.write(JSON.toJSONString(FAVORITE_ITEM_LIST));
        } catch (Exception e) {
            log.error("Failed to save favorite configuration.", e);
        }
    }
}
