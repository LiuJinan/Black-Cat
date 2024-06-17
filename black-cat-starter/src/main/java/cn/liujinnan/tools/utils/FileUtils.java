package cn.liujinnan.tools.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
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

    /**
     * Deletes the specified folder and all the files and sub folders it contains
     *
     * @param folderPath The path to the folder, for example "C:\\myFolder" or "/home/user/myFolder"
     * @return Returns true if the folder was successfully deleted; returns false if the folder does not exist or cannot be deleted.
     */
    public static boolean deleteFolder(String folderPath) {
        Path path = Paths.get(folderPath);
        if (!Files.exists(path)) {
            return false;
        }

        try {

            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    file.toFile().delete();
//                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
            return true;
        } catch (IOException e) {
            log.error("Error deleting folder. ", e);
            return false;
        }
    }
}
