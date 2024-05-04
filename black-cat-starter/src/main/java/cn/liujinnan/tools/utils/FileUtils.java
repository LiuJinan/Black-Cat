package cn.liujinnan.tools.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @description:
 * @author: ljn
 * @create: 2024-05-04 14:01
 **/
@Slf4j
public class FileUtils {

    public static String getFileHashCode(String filePath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            try (InputStream is = new FileInputStream(filePath)) {
                byte[] buffer = new byte[1024];
                int read = 0;
                while ((read = is.read(buffer)) != -1) {
                    digest.update(buffer, 0, read);
                }
            }
            StringBuilder sb = new StringBuilder();
            for (byte b : digest.digest()) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("Get file hashcode error, file={}", filePath, e);
        }
        return null;
    }
}
