package com.duoqu.commons.fmj.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created by tonydeng on 15/4/17.
 */
public class FileUtils {
    private static final String SRCEENTSHOT_DIR = "srceent";
    private static final String JPG = ".jpg";
    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    public static boolean createDirectory(String path) {
        if (StringUtils.isNotEmpty(path)) {
            File dir = new File(path);
            if (dir.exists()) {
                return dir.isDirectory();
            } else {
                return dir.mkdirs();
            }
        }
        return false;
    }

    public static File getSrceentshotOutputByInput(File input) {
        if (input.exists()) {
            File outputPath = new File(input.getParent() + File.separator + SRCEENTSHOT_DIR);
            outputPath.mkdirs();
            return new File(
                    outputPath.getAbsolutePath()
                            + File.separator
                            + EncriptUtils.md5(input.getAbsolutePath()).substring(0, 6).toLowerCase()
                            + JPG
            );
        }
        return null;
    }
}
