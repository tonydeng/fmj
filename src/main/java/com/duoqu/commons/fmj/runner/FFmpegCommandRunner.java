package com.duoqu.commons.fmj.runner;

import com.duoqu.commons.fmj.model.HLS;
import com.duoqu.commons.fmj.model.VideoInfo;
import com.duoqu.commons.fmj.utils.FFmpegUtils;
import com.duoqu.commons.fmj.utils.FileUtils;
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
    public static ProcessBuilder pb = null;
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
            List<String> commands = Lists.newArrayList(BaseCommandOption.getFfmpegBinary());
            commands.addAll(BaseCommandOption.toInputCommonsCmdArrays(input.getAbsolutePath()));
            if (log.isInfoEnabled())
                log.info("get video info commands : '{}'", FFmpegUtils.ffmpegCmdLine(commands));
            try {
                pb = new ProcessBuilder();
                pb.command(commands);

                pb.redirectErrorStream(true);


                pro = pb.start();
                BufferedReader buf = null;
                buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = buf.readLine()) != null) {
                    sb.append(line);
                    continue;
                }

                int ret = pro.waitFor();
                if (log.isInfoEnabled())
                    log.info("get video info process run status:'{}'", ret);
                VideoInfo mi = FFmpegUtils.regInfo(sb.toString());
                mi.setSize(new FileInputStream(input).available());
                return mi;
            } catch (IOException e) {
                if (log.isErrorEnabled())
                    log.error("video '{}' get info IOException :'{} '", input.getAbsoluteFile(), e.getCause().getMessage());
                e.printStackTrace();
            } catch (InterruptedException e) {
                if (log.isErrorEnabled())
                    log.error("video '{}' get info InterruptedException :'{} '", input.getAbsoluteFile(), e.getCause().getMessage());
                e.printStackTrace();
            }
        } else {
            if (log.isErrorEnabled())
                log.error("video '{}' is not fount! ", input.getAbsolutePath());
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
        File output = FileUtils.getSrceentshotOutputByInput(input);
        if (output != null) {
            VideoInfo vi = getVideoInfo(input);
            List<String> commands = Lists.newArrayList(BaseCommandOption.getFfmpegBinary());
            commands.addAll(BaseCommandOption.toScreenshotCmdArrays(input.getAbsolutePath(), output.getAbsolutePath(), shotSecond, vi));
            if (log.isInfoEnabled()) {
                log.info("screenshot commands :'{}'", FFmpegUtils.ffmpegCmdLine(commands));
            }
            try {
                pb = new ProcessBuilder();
                pb.command(commands);
                pb.redirectErrorStream(true);
                pro = pb.start();
                int ret = pro.waitFor();
                if (log.isInfoEnabled())
                    log.info("screent process run status:'{}'", ret);
                if (output != null && output.exists())
                    return output;
            } catch (IOException e) {
                if (log.isErrorEnabled())
                    log.error("video '{}' screenshot IOException :'{} '", input.getAbsoluteFile(), e.getCause().getMessage());
                e.printStackTrace();
            } catch (InterruptedException e) {
                if (log.isErrorEnabled())
                    log.error("video '{}' screenshot InterruptedException :'{} '", input.getAbsoluteFile(), e.getCause().getMessage());
                e.printStackTrace();
            }
        } else {
            if (log.isErrorEnabled())
                log.error("video '{}' screentshot '{}' create error!", input.getAbsolutePath(), output.getAbsolutePath());
        }
        return null;
    }

    /**
     * 生成HLS
     *
     * @param input
     * @param cutSecond
     * @param tsBaseUrl
     * @return
     */
    public static HLS generationHls(File input, int cutSecond, String tsBaseUrl) {
        File output = FileUtils.getM3U8OutputByInput(input);
        if (output != null) {
            VideoInfo vi = getVideoInfo(input);
            List<String> commands = Lists.newArrayList(BaseCommandOption.getFfmpegBinary());
            commands.addAll(BaseCommandOption.toHLSCmdArrays(input.getAbsolutePath(), output.getAbsolutePath(), cutSecond, tsBaseUrl, vi));
            if (log.isInfoEnabled()) {
                log.info("generation HLS commands : '{}'", FFmpegUtils.ffmpegCmdLine(commands));
            }
            pb = new ProcessBuilder();
            pb.command(commands);
            try {
                pro = pb.start();
                int ret = pro.waitFor();
                if (log.isInfoEnabled())
                    log.info("screent process run status:'{}'", ret);
                HLS hls = new HLS();
                hls.setM3u8(output);
                hls.setTs(FileUtils.findTS(output));
                return hls;
            } catch (IOException e) {
                if (log.isErrorEnabled())
                    log.error("video '{}' generationHls IOException :'{} '", input.getAbsoluteFile(), e.getCause().getMessage());
                e.printStackTrace();
            } catch (InterruptedException e) {
                if (log.isErrorEnabled())
                    log.error("video '{}' generationHls InterruptedException :'{} '", input.getAbsoluteFile(), e.getCause().getMessage());
                e.printStackTrace();
            }

        } else {
            if (log.isErrorEnabled())
                log.error("vidoe '{}' m3u8 '{}' create error!", input.getAbsolutePath(), output.getAbsolutePath());
        }

        return null;
    }
}
