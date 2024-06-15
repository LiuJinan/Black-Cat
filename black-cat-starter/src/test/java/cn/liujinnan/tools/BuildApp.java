package cn.liujinnan.tools;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ljn
 * @version 1.0
 * @date 2024-06-13 11:46
 */
public class BuildApp {

    private static final Integer JDK_MIN_MAIN_VERSION = 17;

    private static final String JAR_NAME = "BlackCat.jar";

    /**
     * /x/.../Black-Cat
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");

    public static void main(String[] args) throws Exception {

        // check
        preCheck(args);

        // Get jre module
        List<String> modules = getModules();

        //del old folder


        // Create a folder
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String buildPath = PROJECT_PATH + File.separator + "build" + File.separator + System.getProperty("version") + "_" + format;
        File buildFolder = new File(buildPath);
        buildFolder.mkdirs();

        // create jre
        String jrePath = createJre(modules, buildPath);

        // build app
        buildApp(jrePath, "exe", buildPath);
    }

    /**
     *
     * @param jrePath
     * @param type 可以是exe（Windows可执行文件）、pkg（macOS安装包）、deb（Linux deb包）、rpm（Linux rpm包）、dir（目录形式的应用）、image（可执行映像）。默认根据平台决定
     */
    private static void buildApp(String jrePath, String type, String buildFolder){
        // jpackage --type app-image --input blackCat --runtime-image out --name BlackCat --main-jar BlackCat.jar --icon logo.png --app-version 1.0.0 --vendor liujinnan --copyright liujinnan.cn --description tools --dest exe
        String cmd = "jpackage --type #type --input #jarFolder --runtime-image #jrePath --name #name --main-jar #jarName --icon #icon  --app-version #version --vendor liujinnan --copyright liujinnan.cn --description tools --dest #dest";
        File out = new File(buildFolder + File.separator + type);
        out.mkdirs();
        String icoPath = PROJECT_PATH + "/black-cat-starter/src/main/resources/img/logo.ico";
        cmd = cmd
                .replace("#type", "app-image")
                .replace("#jarFolder", getJarFileFolder())
                .replace("#jrePath", jrePath)
                .replace("#name", "BlackCat")
                .replace("#jarName", JAR_NAME)
                .replace("#icon ", icoPath)
                .replace("#version", System.getProperty("version"))
                .replace("#dest", buildFolder+File.separator+type);
        System.out.println(cmd);
        List<String> result = runCmd(cmd);
        System.out.println(result.stream().collect(Collectors.joining("\n")));
    }

    private static String createJre(List<String> moduleList, String buildPath) {
        String modules = moduleList.stream().collect(Collectors.joining(","));
        String outFolder = buildPath + File.separator + "app_jre";

        // jlink --module-path #jarPath --add-modules #modules --output #outFolder
        String cmd = "jlink  --add-modules #modules --output #outFolder";
        cmd = cmd.replace("#jarPath", getJarFilePath())
                .replace("#modules", modules)
                .replace("#outFolder", outFolder);
        runCmd(cmd);
        return outFolder;
    }

    /**
     * get jdk modules
     *
     * @return
     */
    private static List<String> getModules() {
        String jarFilePath = getJarFilePath();
        String cmd = "jdeps -s " + jarFilePath;
        List<String> modules = runCmd(cmd);
        return modules.stream()
                .map(e -> e.replace(JAR_NAME + " -> ", ""))
                .filter(e -> e.contains("."))
                .collect(Collectors.toList());
    }

    private static void preCheck(String[] args) {
        // JDK version cannot be less than 17
        int version = Integer.parseInt(System.getProperty("java.specification.version"));
        if (version < JDK_MIN_MAIN_VERSION) {
            throw new IllegalStateException();
        }

        // jar check
        String jarFilePath = getJarFilePath();
        File file = new File(jarFilePath);
        if (!file.exists()) {
            System.out.println("File [" + jarFilePath + "] does not exist. Please build the package first.");
            throw new IllegalStateException();
        }

        // run with -Dversion=xx
        String appVersion = System.getProperty("version");
        if (StringUtils.isBlank(appVersion)) {
            System.out.println("Please add the run parameter -Dversion");
            throw new IllegalStateException();
        }
    }

    private static String getJarFilePath() {
        return getJarFileFolder() + File.separator + JAR_NAME;
    }

    private static String getJarFileFolder() {
        return PROJECT_PATH + File.separator +
                "black-cat-starter" + File.separator +
                "target";
    }

    private static List<String> runCmd(String cmd) {
        Runtime runtime = Runtime.getRuntime();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                runtime.exec(cmd).getInputStream(), "gbk"))) {
            return br.lines().collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
