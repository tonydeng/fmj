package com.duoqu.commons.fmj.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.List;

/**
 * Created by tonydeng on 15/4/17.
 */
public class FileUtils {
    private static final String PATH_SRCEENTSHOT = "srceent";
    private static final String PATH_HLS = "hls";
    private static final String EXTEND_JPG = ".jpg";
    private static final String EXTEND_M3U8 = ".m3u8";
    private static final String EXTEND_TS = ".ts";

    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    public static boolean createDirectory(String path) {
        if (StringUtils.isNotEmpty(path)) {
            return createDirectory(new File(path));
        }
        return false;
    }

    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    public static boolean createDirectory(File path) {
        if (path.exists()) {
            return path.isDirectory();
        } else {
            return path.mkdirs();
        }
    }

    /**
     * 根据视频文件获得视频截图文件
     *
     * @param input
     * @return
     */
    public static File getSrceentshotOutputByInput(File input) {
        if (input != null && input.exists()) {
            File outputPath = new File(input.getParent() + File.separator + PATH_SRCEENTSHOT);
            if (createDirectory(outputPath)) {
                return new File(
                        outputPath.getAbsolutePath()
                                + File.separator
                                + EncriptUtils.md5(input.getAbsolutePath()).substring(0, 6).toLowerCase()
                                + EXTEND_JPG
                );
            }
        }
        return null;
    }

    /**
     * 根据视频文件获得HLS的m3u8文件
     *
     * @param input
     * @return
     */
    public static File getM3U8OutputByInput(File input) {
        if (input != null && input.exists()) {
            String videoName = input.getName().substring(0, input.getName().indexOf(".")).toLowerCase();
            File outputPath = new File(input.getParent() + File.separator + PATH_HLS + File.separator + videoName);
            if (createDirectory(outputPath)) {
                return new File(outputPath.getAbsolutePath()
                        + File.separator
                        + videoName
                        + EXTEND_M3U8);
            }
        }
        return null;
    }

    /**
     * 通过m3u8文件获得该目录下的所有ts文件
     *
     * @param m3u8
     * @return
     */
    public static List<File> findTS(File m3u8) {
        if (m3u8 != null && m3u8.exists()) {
            final File path = m3u8.getParentFile();
            if (path.isDirectory()) {
                FilenameFilter filter = new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(EXTEND_TS);
                    }
                };
                return Lists.newArrayList(path.listFiles(filter));
            }
        }
        return Collections.EMPTY_LIST;
    }
}
