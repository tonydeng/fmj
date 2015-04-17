package com.duoqu.commons.fmj.runner;

import com.duoqu.commons.fmj.model.HLS;
import com.duoqu.commons.fmj.model.VideoInfo;
import com.duoqu.commons.fmj.utils.FFmpegUtils;
import com.duoqu.commons.fmj.utils.FileUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
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
     *
     * @param input
     * @return
     */
    public static VideoInfo getVideoInfo(File input) {
        if (input != null && input.exists()) {
            List<String> commands = Lists.newArrayList();
            commands.addAll(BaseCommandOption.toCommonsCmdArrays(input.getAbsolutePath()));
            if (log.isDebugEnabled())
                log.debug("get video info commands : '{}'", FFmpegUtils.ffmpegCmdLine(commands));
            try {
                ProcessBuilder pb = new ProcessBuilder();
                pb.command(commands);

                pb.redirectErrorStream(true);


                pro = pb.start();
                BufferedReader buf = null;
                buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = buf.readLine()) != null) {
                    sb.append(line);
//                System.out.println(line);
                    continue;
                }
//            System.out.println();

                int ret = pro.waitFor();
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
     *
     * @param input
     * @param shotSecond
     * @return
     */
    public static File screenshot(File input, int shotSecond) {
        if (input != null && input.exists()) {
            File output = FileUtils.getSrceentshotOutputByInput(input);
            List<String> commands = Lists.newArrayList();
            commands.addAll(BaseCommandOption.toCommonsCmdArrays(input.getAbsolutePath()));
            commands.addAll(BaseCommandOption.toScreenshotCmdArrays(output.getAbsolutePath(), shotSecond));
            if (log.isDebugEnabled()) {
                log.debug("screenshot commands :'{}'", FFmpegUtils.ffmpegCmdLine(commands));
            }
            try {
                ProcessBuilder pb = new ProcessBuilder();
                pb.command(commands);

                pb.redirectErrorStream(true);


                pro = pb.start();
//                BufferedReader buf = null;
//                buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                StringBuffer sb = new StringBuffer();
                int ret = pro.waitFor();
                if (log.isDebugEnabled())
                    log.debug("process run status:'{}'", ret);
                if (output != null && output.exists())
                    return output;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 生成HLS
     *
     * @param input
     * @param outputPath
     * @param cutSecond
     * @param tsBaseUrl
     * @return
     */
    public static HLS generationHls(File input, String outputPath, int cutSecond, String tsBaseUrl) {
        if (input != null && input.exists()) {
            if (StringUtils.isNotEmpty(outputPath)
                    && FileUtils.createDirectory(outputPath)) {
                List<String> commands = Lists.newArrayList();
                commands.addAll(BaseCommandOption.toCommonsCmdArrays(input.getAbsolutePath()));
                commands.addAll(BaseCommandOption.toHLSCmdArrays(outputPath, cutSecond, tsBaseUrl));
                if (log.isDebugEnabled()) {
                    log.debug("generation HLS commands : '{}'", FFmpegUtils.ffmpegCmdLine(commands));
                }
            }


        }
        return null;
    }
}
