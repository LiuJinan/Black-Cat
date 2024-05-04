package cn.liujinnan.tools.utils;

/**
 * @description:
 * @author: ljn
 * @create: 2024-05-04 12:08
 **/
import java.awt.Color;

public class ColorUtils {

    /**
     * 加深颜色的方法
     * @param originalColor 需要加深的颜色
     * @param factor 加深因子，0.1，颜色加深10%
     * @return
     */
    public static Color darkenColor(Color originalColor, double factor) {
        int darkerRed = (int) Math.max(0, originalColor.getRed() - originalColor.getRed() * factor);
        int darkerGreen = (int) Math.max(0, originalColor.getGreen() - originalColor.getGreen() * factor);
        int darkerBlue = (int) Math.max(0, originalColor.getBlue() - originalColor.getBlue() * factor);
        return new Color(darkerRed, darkerGreen, darkerBlue);
    }
}
