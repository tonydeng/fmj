package com.duoqu.commons.fmj.runner;

import com.duoqu.commons.fmj.model.HLS;
import com.duoqu.commons.fmj.model.VideoFile;
import com.duoqu.commons.fmj.model.VideoInfo;
import com.duoqu.commons.fmj.utils.FFmpegUtils;
import com.duoqu.commons.fmj.utils.FileUtils;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tonydeng on 15/4/16.
 */
public class FFmpegCommandRunner {
    private static final Logger log = LoggerFactory.getLogger(FFmpegCommandRunner.class);
    private static ProcessBuilder pb = null;
    private static Process pro = null;

    /**
     * 获取视频信息
     *
     * @param input
     * @return
     */
    public static VideoInfo getVideoInfo(File input) {
        VideoInfo vi = new VideoInfo();
        if (input != null && input.exists()) {
            List<String> commands = Lists.newArrayList(BaseCommandOption.getFFprobeBinary());
            commands.add(input.getAbsolutePath());
            vi.setSize(FileUtils.getFineSize(input));
            if (vi.getSize() > 0) {
                return FFmpegUtils.regInfo(runProcess(commands), vi);
            }
        } else {
            if (log.isErrorEnabled())
                log.error("video '{}' is not fount! ", input.getAbsolutePath());
        }

        return vi;
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
            List<String> commands = Lists.newArrayList(BaseCommandOption.getFFmpegBinary());
            commands.addAll(BaseCommandOption.toScreenshotCmdArrays(input.getAbsolutePath(), output.getAbsolutePath(), shotSecond, vi));
//            if (log.isInfoEnabled()) {
//                log.info("screenshot commands :'{}'", FFmpegUtils.ffmpegCmdLine(commands));
//            }
//            try {
//                pb = new ProcessBuilder();
//                pb.command(commands);
//                pb.redirectErrorStream(true);
//                pro = pb.start();
//                int ret = pro.waitFor();
//                if (log.isInfoEnabled())
//                    log.info("screent process run status:'{}'", ret);
//                if (output != null && output.exists())
//                    return output;
//            } catch (IOException e) {
//                if (log.isErrorEnabled())
//                    log.error("video '{}' screenshot IOException :'{} '", input.getAbsoluteFile(), e.getCause().getMessage());
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                if (log.isErrorEnabled())
//                    log.error("video '{}' screenshot InterruptedException :'{} '", input.getAbsoluteFile(), e.getCause().getMessage());
//                e.printStackTrace();
//            }
            if (StringUtils.isNotEmpty(runProcess(commands))) {
                return output;
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
            List<String> commands = Lists.newArrayList(BaseCommandOption.getFFmpegBinary());
            commands.addAll(BaseCommandOption.toHLSCmdArrays(input.getAbsolutePath(), output.getAbsolutePath(), cutSecond, tsBaseUrl, vi));
//            if (log.isInfoEnabled()) {
//                log.info("generation HLS commands : '{}'", FFmpegUtils.ffmpegCmdLine(commands));
//            }
//            pb = new ProcessBuilder();
//            pb.command(commands);
//            try {
//                pro = pb.start();
//                int ret = pro.waitFor();
//                if (log.isInfoEnabled())
//                    log.info("screent process run status:'{}'", ret);
//                HLS hls = new HLS();
//                hls.setM3u8(output);
//                hls.setTs(FileUtils.findTS(output));
//                return hls;
//            } catch (IOException e) {
//                if (log.isErrorEnabled())
//                    log.error("video '{}' generationHls IOException :'{} '", input.getAbsoluteFile(), e.getCause().getMessage());
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                if (log.isErrorEnabled())
//                    log.error("video '{}' generationHls InterruptedException :'{} '", input.getAbsoluteFile(), e.getCause().getMessage());
//                e.printStackTrace();
//            }
            if (StringUtils.isNotEmpty(runProcess(commands))) {
                HLS hls = new HLS();
                hls.setM3u8(output);
                hls.setTs(FileUtils.findTS(output));
                return hls;
            }

        } else {
            if (log.isErrorEnabled())
                log.error("vidoe '{}' m3u8 '{}' create error!", input.getAbsolutePath(), output.getAbsolutePath());
        }

        return null;
    }

    /**
     * 转换视频格式为MP4
     *
     * @param input
     * @return
     */
    public static VideoFile coverToMp4(File input) {
        VideoFile vf = new VideoFile(input, FileUtils.getMp4OutputByInput(input));
        if (vf.getTarget() != null) {
            vf.setInputInfo(getVideoInfo(input));
            if (vf.getInputInfo().getSize() > 0
                    && !BaseCommandOption.H264.equals(vf.getInputInfo().getFormat())) {
                List<String> commands = Lists.newArrayList(BaseCommandOption.getFFmpegBinary());
                commands.addAll(BaseCommandOption.toMP4CmdArrays(input.getAbsolutePath(), vf.getTarget().getAbsolutePath()));

                if (StringUtils.isNotEmpty(runProcess(commands))) {
                    vf.setTargetInfo(getVideoInfo(vf.getTarget()));
                    vf.setSuccess(true);
                    return vf;
                }
            }

        }
        return vf;
    }

    /**
     * 执行命令
     *
     * @param commands
     * @return
     */
    private static String runProcess(List<String> commands) {
        try {
            return runProcess(commands, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行命令
     *
     * @param commands
     * @param handler
     * @return
     * @throws Exception
     */
    private static String runProcess(List<String> commands, ProcessCallbackHandler handler) throws Exception {
        if (log.isDebugEnabled())
            log.debug("start to run ffmpeg process... cmd : '{}'", FFmpegUtils.ffmpegCmdLine(commands));
        Stopwatch stopwatch = Stopwatch.createStarted();
        pb = new ProcessBuilder();
        pb.command(commands);

        pro = pb.start();

        if (null == handler) {
            handler = new DefaultProcessCallbackHandler();
        }
        if (log.isInfoEnabled())
            log.info("inpuStream:'{}'", handler.handler(pro.getInputStream()));

        String result = null;
        try {
            result = handler.handler(pro.getErrorStream());
        } catch (Exception e) {
            log.error("errorStream:{}", result, e);
        }

        try {
            int flag = pro.waitFor();
            if (flag != 0) {
                throw new IllegalThreadStateException("process exit with error value : " + flag);
            }
        } catch (InterruptedException e) {
            log.error("wait for process finish error:{}", e);
        } finally {
            if (null != pro)
                pro.destroy();
            stopwatch.stop();
        }
        if (log.isInfoEnabled()) {
            log.info("ffmpeg run {} seconds, {} milliseconds",
                    stopwatch.elapsed(TimeUnit.SECONDS),
                    stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
        return result;
    }
}
