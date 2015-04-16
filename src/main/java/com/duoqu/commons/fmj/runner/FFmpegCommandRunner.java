package com.duoqu.commons.fmj.runner;

import com.duoqu.commons.fmj.model.VideoInfo;
import com.duoqu.commons.fmj.utils.FFmpegUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Created by tonydeng on 15/4/16.
 */
public class FFmpegCommandRunner {
    private static final Logger log = LoggerFactory.getLogger(FFmpegCommandRunner.class);


    public static Process pro = null;

    private volatile static boolean isRunning = false;

    /**
     * 获取视频信息
     * @param input
     * @return
     */
    public static VideoInfo getVideoInfo(File input) {
        if (input != null && input.exists()) {
            List<String> commands = Lists.newArrayList();
            commands.addAll(BaseCommandOption.toCommonsCmdArrays(input.getAbsolutePath()));
            try {
                ProcessBuilder pb = new ProcessBuilder();
                pb.command(commands);

                pb.redirectErrorStream(true);


                Process p = pb.start();
                BufferedReader buf = null;
                buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = buf.readLine()) != null) {
                    sb.append(line);
//                System.out.println(line);
                    continue;
                }
//            System.out.println();

                int ret = p.waitFor();
                VideoInfo mi = FFmpegUtils.regInfo(sb.toString());
                mi.setSize(new FileInputStream(input).available());
                return mi;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 视频截图
     * @param input
     * @param output
     * @param shotSecond
     * @return
     */
    public static File screenshot(File input, String output, int shotSecond) {
        if (input != null && input.exists()) {
            List<String> commands = Lists.newArrayList();
            commands.addAll(BaseCommandOption.toCommonsCmdArrays(input.getAbsolutePath()));
            commands.addAll(BaseCommandOption.toScreenshotCmdArrays(output, shotSecond));
            if (log.isDebugEnabled()) {
                log.debug("commands :'{}'", commands);
            }
            try {
                ProcessBuilder pb = new ProcessBuilder();
                pb.command(commands);

                pb.redirectErrorStream(true);


                Process p = pb.start();
                BufferedReader buf = null;
                buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
                StringBuffer sb = new StringBuffer();
                int ret = p.waitFor();
                if (log.isDebugEnabled())
                    log.debug("process run status:'{}'", ret);
                File outputFile = new File(output);
                if (outputFile != null && outputFile.exists())
                    return outputFile;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
