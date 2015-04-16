package com.duoqu.commons.fmj.runner;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by tonydeng on 15/4/15.
 */
public class BaseCommandOption {
    private static final Logger log = LoggerFactory.getLogger(BaseCommandOption.class);
    private static boolean isWin = false;
    private static boolean isLinux = false;
    private static List<String> FFMPEG_BINARY;

    public static final String WINCMD = "cmd";

    public static final String WINCMDOP = "/c";

    public static final String LINUXCMD = "/usr/bin/env";

    public static final String FFMPEG = "ffmpeg";

    public static final String Y = "-y";

    public static final String INPUT = "-i";

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

    public static List<String> getFfmpegBinary() {
        if (FFMPEG_BINARY == null) {
            if (isWin) {
                FFMPEG_BINARY = Lists.newArrayList(WINCMD, WINCMDOP, FFMPEG);
            } else if (isLinux) {
                FFMPEG_BINARY = Lists.newArrayList(LINUXCMD, FFMPEG);
            }
        }
        return FFMPEG_BINARY;
    }

    public static List<String> toCommonsCmdArrays(String input) {
        List<String> commands = Lists.newArrayList();
        commands.addAll(getFfmpegBinary());
        commands.add(INPUT);
        commands.add(input);

        return commands;
    }

    public static List<String> toScreenshotCmdArrays(String output, int shotSecond) {
        List<String> commands = Lists.newArrayList();
        commands.add(BaseCommandOption.Y);
        commands.add("-f");
        commands.add("image2");
        commands.add("-t");
        commands.add("0.001");
        commands.add("-ss");
        commands.add(String.valueOf(shotSecond));
        commands.add(output);
        return commands;
    }

}
