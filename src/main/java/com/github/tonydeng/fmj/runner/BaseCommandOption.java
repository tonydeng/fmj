package com.github.tonydeng.fmj.runner;

import com.github.tonydeng.fmj.model.VideoInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by tonydeng on 15/4/15.
 */
@Slf4j
public class BaseCommandOption {
    private static boolean isWin = false;
    private static boolean isLinux = false;
    private static Integer defaultIoThreads = 1;

    private static List<String> ffmpegBinary;
    private static List<String> ffprobeBinary;

    public static final String WINCMD = "cmd";
    public static final String WINCMDOP = "/c";
    public static final String LINUXCMD = "/usr/bin/env";
    public static final String FFMPEG = "ffmpeg";
    public static final String FFPROBE = "ffprobe";

    public static final String Y = "-y";
    public static final String INPUT = "-i";
    public static final String T = "-t";
    public static final String F = "-f";
    public static final String S = "-s";
    public static final String SS = "-ss";
    public static final String CV = "-c:v";
    public static final String CA = "-c:a";
    public static final String STRICT = "-strict";
    public static final String VF = "-vf";
    public static final String THREADS = "-threads";

    public static final String COPY = "copy";

    public static final String FORMAT_HLS = "hls";
    public static final String FORMAT_IMAGE = "image2";
    public static final String FORMAT_LIB264 = "libx264";
    public static final String FORMAT_ACC = "aac";

    public static final String HLS_TIME = "-hls_time";
    public static final String HLS_LIST_SIZE = "-hls_list_size";
    public static final String HLS_WRAP = "-hls_wrap";
    public static final String HLS_BASE_URL = "-hls_base_url";


    public static final String H264 = "h264";
    public static final String UTF8 = "utf-8";

    static {
        String env = System.getProperty("os.name");
        log.debug("current operate system :{}", env);

        if (StringUtils.isNotEmpty(env)) {
            String os = env.toLowerCase();
            if (os.contains("win")) {
                isWin = true;
            } else if (os.contains("linux") || os.contains("mac")) {
                isLinux = true;
            }
        }
        //获得当前机器的CPU核数
        defaultIoThreads = Runtime.getRuntime().availableProcessors();
        if (log.isDebugEnabled()) {
            log.debug("isWindows : '{}'  or isLinux:'{}' defaultIoThreads:'{}'",
                    isWin, isLinux, defaultIoThreads);
        }
    }

    private BaseCommandOption() {

    }

    /**
     * 得到ffmpeg命令参数
     *
     * @return
     */
    public static List<String> getFFmpegBinary() {
        if (ffmpegBinary == null) {
            if (isWin) {
                ffmpegBinary = Lists.newArrayList(WINCMD, WINCMDOP, FFMPEG);
            } else if (isLinux) {
                ffmpegBinary = Lists.newArrayList(LINUXCMD, FFMPEG);
            }
        }
        return ffmpegBinary;
    }

    /**
     * 得到ffprobe命令
     *
     * @return
     */
    public static List<String> getFFprobeBinary() {
        if (null == ffprobeBinary) {
            if (isWin) {
                ffprobeBinary = Lists.newArrayList(WINCMD, WINCMDOP, FFPROBE);
            } else if (isLinux) {
                ffprobeBinary = Lists.newArrayList(LINUXCMD, FFPROBE);
            }
        }
        return ffprobeBinary;
    }

    /**
     * 视频输入的命令参数
     *
     * @param input
     * @return
     */
    public static List<String> toInputCommonsCmdArrays(String input) {
        return Lists.newArrayList(
                INPUT, input
        );
    }

    /**
     * 截图的命令参数
     *
     * @param input
     * @param output
     * @param shotSecond
     * @param vi
     * @return
     */
    public static List<String> toScreenshotCmdArrays(String input, String output, int shotSecond, VideoInfo vi) {
        if (vi != null && vi.getSize() > 0) {
            List<String> commands = Lists.newArrayList();
            if (vi.getDuration() < shotSecond) {
                shotSecond = 1;
            }
            commands.add(SS);
            commands.add(String.valueOf(shotSecond));

            commands.addAll(toInputCommonsCmdArrays(input));

            commands.addAll(getRoateCmdArrays(vi));

            commands.add(T);
            commands.add("0.001");
            commands.add(Y);
            commands.add(F);
            commands.add(FORMAT_IMAGE);


            commands.add(output);
            return commands;
        }
        return Collections.emptyList();
    }

    /**
     * 转成HLS的命令参数
     *
     * @param input
     * @param m3u8Output
     * @param cutSecond
     * @param tsBaseUrl
     * @param vi
     * @return
     */
    public static List<String> toHLSCmdArrays(String input, String m3u8Output, int cutSecond, String tsBaseUrl, VideoInfo vi) {
        if (vi != null && vi.getSize() > 0) {
            List<String> commands = Lists.newArrayList(toInputCommonsCmdArrays(input));
            commands.addAll(getRoateCmdArrays(vi));
            commands.addAll(Lists.newArrayList(
                    CV, FORMAT_LIB264,
                    CA, FORMAT_ACC,
                    STRICT, "-2",
                    F, FORMAT_HLS,
                    THREADS, defaultIoThreads.toString(),
                    HLS_TIME, String.valueOf(cutSecond),
                    HLS_LIST_SIZE, "0",
                    HLS_WRAP, "0",
                    HLS_BASE_URL, tsBaseUrl,
                    m3u8Output
            ));

            return commands;
        }
        return Collections.emptyList();
    }

    /**
     * 转码到mp4的命令参数
     *
     * @param input
     * @param output
     * @param vi
     * @return
     */
    public static List<String> toMP4CmdArrays(String input, String output, VideoInfo vi) {
        if (vi != null && vi.getSize() > 0) {
            List<String> commands = Lists.newArrayList(toInputCommonsCmdArrays(input));
            commands.addAll(getRoateCmdArrays(vi));
            commands.addAll(Lists.newArrayList(
                    CV, FORMAT_LIB264,
                    CA, FORMAT_ACC,
                    STRICT, "-2",
                    THREADS, defaultIoThreads.toString(),
                    output
            ));
            return commands;
        }
        return Collections.emptyList();
    }

    /**
     * 获取视频转向的参数
     *
     * @param vi
     * @return
     */
    public static List<String> getRoateCmdArrays(VideoInfo vi) {
        if (vi != null && vi.getSize() > 0 && vi.getRotate() > 0) {
            //-vf "transpose=1" -c:a copy
            return Lists.newArrayList(
                    VF, "transpose=1"
//                        CA,COPY
            );
        }
        return Collections.emptyList();
    }
}
