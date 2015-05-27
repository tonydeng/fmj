package com.duoqu.commons.fmj.runner;

import com.duoqu.commons.fmj.BaseTest;
import com.duoqu.commons.fmj.model.HLS;
import com.duoqu.commons.fmj.model.VideoFile;
import com.duoqu.commons.fmj.model.VideoInfo;
import com.duoqu.commons.fmj.utils.FFmpegUtils;
import com.duoqu.commons.fmj.utils.FileUtils;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tonydeng on 15/4/16.
 */
@Ignore
public class FFmpegCommandRunnerTest extends BaseTest {
    private static final List<File> inputs_win = Lists.newArrayList(
            new File("D:\\temp\\test2.mp4"),
            new File("D:\\temp\\test.flv")
    );
    private static final List<File> inputs_linux = Lists.newArrayList(
            new File("/home/chichen/test/test.flv"),
            new File("/home/chichen/test//test2.mp4")
    );
    private static final List<File> inputs_mac = Lists.newArrayList(
//            new File("/Users/tonydeng/temp/m3u8/2013.flv"),
//
            new File("/users/tonydeng/temp/m3u8/2013.flv"),
            new File("/users/tonydeng/temp/m3u8/IMG_1666.MOV")
//            new File("/Users/tonydeng/temp/m3u8/IMG_1666.MOV")

    );
    private static List<File> inputs;

    @Before
    public void init() {
        String env = System.getProperty("os.name");
        if (null != env) {
            String os = env.toLowerCase();
            if (os.indexOf("win") >= 0) {
                log.info("****************** is WIN OS");
                inputs = inputs_win;
            } else if (os.indexOf("linux") >= 0) {
                log.info("****************** is LINUX OS");
                inputs = inputs_linux;
            } else if (os.indexOf("mac") >= 0) {
                log.info("****************** is MAC OS");
                inputs = inputs_mac;
            }
        }
    }

//    @Test
    public void getVideoInfoTest() {

        for (File input : inputs) {
            VideoInfo vi = FFmpegCommandRunner.getVideoInfo(input);
            log.info("{} video info: {}", input.getAbsoluteFile(), vi.toString());
        }
    }

//        @Test
    public void screenshotTest() {
        for (File input : inputs) {
            File output = FFmpegCommandRunner.screenshot(input,
                    10);
            log.info("input : {}, output:{}", input.getAbsoluteFile(), output.getAbsoluteFile());
        }
    }

        @Test
    public void generationHlsTest() {
        for (File input : inputs) {
            HLS hls = FFmpegCommandRunner.generationHls(input, 3, "http://dl.duoquyuedu.com/m3u8/"+ FileUtils.getFileName(input)+"/");
            if (hls != null) {
                log.info("m3u8 path:'{}'", hls.getM3u8().getAbsolutePath());
                for (File ts : hls.getTs()) {
                    log.info("ts path:'{}'", ts.getAbsolutePath());
                }
            }
        }
    }

//    @Test
    public void coverToMp4Test() {
        for (File input : inputs) {
            VideoFile vf = FFmpegCommandRunner.coverToMp4(input);
            if (vf.isSuccess())
                log.info(vf.toString());
        }
    }
//    @Test
    public void parallelCoverMp4Test(){
        for(File input:inputs){
            VideoInfo vi = FFmpegCommandRunner.getVideoInfo(input);
            List<String> commands = Lists.newArrayList(
                    "parallel",
                    "--will-cite",
                    "-j","64",
                    "ffmpeg",
                    "-i","{}",
                    "-vf","transpose=1",
                    "-c:v","ibx264",
                    "-c:v","libx264","-c:a","aac",
                    " -strict","-2","-threads","8",
                    "{.}_paraller.mp4",
                    ":::",input.getAbsolutePath()
            );

//            log.info(FFmpegUtils.ffmpegCmdLine(commands));
            FFmpegCommandRunner.runProcess(commands);
    }




//        commands.addAll(BaseCommandOption.toMP4CmdArrays(,"/Users/tonydeng/temp/m3u8/MG_1666.mp4"));

    }
}
