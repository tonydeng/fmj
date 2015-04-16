package com.duoqu.commons.fmj.runner;

import com.duoqu.commons.fmj.BaseTest;
import com.duoqu.commons.fmj.model.VideoInfo;
import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.util.List;

/**
 * Created by tonydeng on 15/4/16.
 */
@Ignore
public class FFmpegCommandRunnerTest extends BaseTest {
    private static final List<File> inputs = Lists.newArrayList(
            new File("/Users/tonydeng/temp/m3u8/muse_va_v97.mp4"),
            new File("/Users/tonydeng/Downloads/XLDownload/038_3xplanet_NHDTA-587.avi")
    );

    @Test
    public void getVideoInfoTest() {

        for (File input : inputs) {
            VideoInfo vi = FFmpegCommandRunner.getVideoInfo(input);
            log.info("{} video info: {}", input.getAbsoluteFile(), vi.toString());
        }
    }

    @Test
    public void screenshotTest() {
        for (File input : inputs) {
            String outputPath = "/Users/tonydeng/temp/screenshot/" + DigestUtils.md5DigestAsHex(new String(input.getAbsolutePath()).getBytes()) + ".jpg";
            File output = FFmpegCommandRunner.screenshot(input,
                    outputPath,
                    10);

            log.info("input : {}, output:{}", input.getAbsoluteFile(), output.getAbsoluteFile());
        }
    }
}
