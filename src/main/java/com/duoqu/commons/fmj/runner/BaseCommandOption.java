package com.duoqu.commons.fmj.runner;

import com.duoqu.commons.fmj.model.VideoInfo;
import com.duoqu.commons.fmj.model.VideoResolution;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tonydeng on 15/4/15.
 */
public class BaseCommandOption {
    private static final Logger log = LoggerFactory.getLogger(BaseCommandOption.class);
    private static boolean isWin = false;
    private static boolean isLinux = false;
    private static List<String> FFMPEG_BINARY;
    private static List<String> FFPROBE_BINARY;

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

    public static final String COPY = "copy";

    public static final String FORMAT_HLS = "hls";
    public static final String FORMAT_IMAGE = "image2";
    public static final String FORMAT_LIB264 = "libx264";
    public static final String FORMAT_ACC = "aac";

    public static final String HLS_TIME = "-hls_time";
    public static final String HLS_LIST_SIZE = "-hls_list_size";
    public static final String HLS_WRAP = "-hls_wrap";
    public static final String HLS_BASE_URL = "-hls_base_url";

    public static final  String UTF8 = "utf-8";

    static {
        String env = System.getProperty("os.name");
        if (log.isDebugEnabled())
            log.debug("current operate system :{}", env);

        if (null != env) {
            String os = env.toLowerCase();
            if (os.indexOf("win") >= 0) {
                isWin = true;
            } else if (os.indexOf("linux") >= 0 || os.indexOf("mac") >= 0) {
                isLinux = true;
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("isWindows : '{}'  or isLinux:'{}' ", isWin, isLinux);
        }
    }

    public static List<String> getFFmpegBinary() {
        if (FFMPEG_BINARY == null) {
            if (isWin) {
                FFMPEG_BINARY = Lists.newArrayList(WINCMD, WINCMDOP, FFMPEG);
            } else if (isLinux) {
                FFMPEG_BINARY = Lists.newArrayList(LINUXCMD, FFMPEG);
            }
        }
        return FFMPEG_BINARY;
    }

    public static List<String> getFFprobeBinary(){
        if(null == FFPROBE_BINARY){
            if(isWin){
                FFPROBE_BINARY = Lists.newArrayList(WINCMD,WINCMDOP,FFPROBE);
            }else if(isLinux){
                FFPROBE_BINARY = Lists.newArrayList(LINUXCMD,FFPROBE);
            }
        }
        return FFPROBE_BINARY;
    }

    public static List<String> toInputCommonsCmdArrays(String input) {
        return Lists.newArrayList(
                INPUT, input
        );
    }

    public static List<String> toScreenshotCmdArrays(String input, String output, int shotSecond, VideoInfo vi) {
        if (vi != null && vi.getSize() > 0) {
            List<String> commands = Lists.newArrayList();
            if (vi.getDuration() < Long.valueOf(shotSecond).longValue()) {
                shotSecond = 1;
            }
            commands.add(SS);
            commands.add(String.valueOf(shotSecond));

            commands.addAll(toInputCommonsCmdArrays(input));

            if(vi.getRotate() > 0){
                //-vf "transpose=1" -c:a copy
                commands.add(VF);
                commands.add("transpose=1");
                commands.add(CA);
                commands.add(COPY);
            }
            commands.add(T);
            commands.add("0.001");
            commands.add(Y);
            commands.add(F);
            commands.add(FORMAT_IMAGE);



            commands.add(output);
            return commands;
        }
        return Collections.EMPTY_LIST;
    }

    public static List<String> toHLSCmdArrays(String input, String m3u8Output, int cutSecond, String tsBaseUrl, VideoInfo vi) {
        if (vi != null && vi.getSize() > 0) {
            List<String> commands = Lists.newArrayList(toInputCommonsCmdArrays(input));
            commands.addAll(Lists.newArrayList(CV, FORMAT_LIB264,
                    CA, FORMAT_ACC,
                    STRICT, "-2",
                    F, FORMAT_HLS,
                    HLS_TIME, String.valueOf(cutSecond),
                    HLS_LIST_SIZE, "0",
                    HLS_WRAP, "0",
                    HLS_BASE_URL, tsBaseUrl,
                    m3u8Output));

            return commands;
        }
        return Collections.EMPTY_LIST;
    }
}
