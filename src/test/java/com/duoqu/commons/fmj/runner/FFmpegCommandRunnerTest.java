package com.duoqu.commons.fmj.runner;

import com.duoqu.commons.fmj.BaseTest;
import com.duoqu.commons.fmj.model.HLS;
import com.duoqu.commons.fmj.model.VideoFile;
import com.duoqu.commons.fmj.model.VideoInfo;
import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created by tonydeng on 15/4/16.
 */
@Ignore
public class FFmpegCommandRunnerTest extends BaseTest {
    private static final List<File> inputs = Lists.newArrayList(
//            new File("/Users/tonydeng/temp/m3u8/muse_va_v97.mp4"),
//            new File("/Users/tonydeng/temp/m3u8/VID_20150414_191241.mp4"),
//            new File("/Users/tonydeng/temp/m3u8/2013.flv"),
//            new File("/users/tonydeng/temp/m3u8/muse_va_v100.flv")
//            new File("D:\\temp\\test_flower.mp4"),
            new File("D:\\temp\\test2.mp4"),
            new File("D:\\temp\\test.flv")
    );

    //    @Test
    public void getVideoInfoTest() {

        for (File input : inputs) {
            VideoInfo vi = FFmpegCommandRunner.getVideoInfo(input);
            log.info("{} video info: {}", input.getAbsoluteFile(), vi.toString());
        }
    }

    //    @Test
    public void screenshotTest() {
        for (File input : inputs) {
            File output = FFmpegCommandRunner.screenshot(input,
                    10);
            log.info("input : {}, output:{}", input.getAbsoluteFile(), output.getAbsoluteFile());
        }
    }

    @Test
    /***
     * cmd /c ffmpeg -i D:\WPORKSPACE\apache-tomcat-7.0.11\webapps\examples\h5\test.flv
     * -c:v libx264 -c:a aac -strict -2 -f hls -hls_time 3
     * -hls_list_size 0 -hls_wrap 0 -hls_base_url
     * http://p.wuguangchang.com/hls/
     * D:\WPORKSPACE\apache-tomcat-7.0.11\webapps\examples\h5\hls\test\test.m3u8
     * **/
    public void generationHlsTest() {
        for (File input : inputs) {
            HLS hls = FFmpegCommandRunner.generationHls(input, 3, "http://p.wuguangchang.com/hls/");
            if (hls != null) {
                log.info("m3u8 path:'{}'", hls.getM3u8().getAbsolutePath());
                for (File ts : hls.getTs()) {
                    log.info("ts path:'{}'", ts.getAbsolutePath());
                }
            }
        }
    }

    @Test
    public void coverToMp4Test() {
        for (File input : inputs) {
            VideoFile vf = FFmpegCommandRunner.coverToMp4(input);
            if (vf.isSuccess())
                log.info(vf.toString());
        }
    }
}
