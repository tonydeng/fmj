package com.duoqu.commons.fmj.utils;

import com.duoqu.commons.fmj.BaseTest;
import com.duoqu.commons.fmj.model.VideoInfo;
import org.junit.Ignore;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tonydeng on 15/4/17.
 */
@Ignore
public class FFmpegUtilsTest extends BaseTest {
    private static final String stdout = "ffprobe version 2.6.1 Copyright (c) 2007-2015 the FFmpeg developers\n" +
            "  built with Apple LLVM version 6.0 (clang-600.0.57) (based on LLVM 3.5svn)\n" +
            "  configuration: --prefix=/usr/local/Cellar/ffmpeg/2.6.1 --enable-shared --enable-pthreads --enable-gpl --enable-version3 --enable-hardcoded-tables --enable-avresample --cc=clang --host-cflags= --host-ldflags= --enable-libx264 --enable-libmp3lame --enable-libvo-aacenc --enable-libxvid --enable-vda\n" +
            "  libavutil      54. 20.100 / 54. 20.100\n" +
            "  libavcodec     56. 26.100 / 56. 26.100\n" +
            "  libavformat    56. 25.101 / 56. 25.101\n" +
            "  libavdevice    56.  4.100 / 56.  4.100\n" +
            "  libavfilter     5. 11.102 /  5. 11.102\n" +
            "  libavresample   2.  1.  0 /  2.  1.  0\n" +
            "  libswscale      3.  1.101 /  3.  1.101\n" +
            "  libswresample   1.  1.100 /  1.  1.100\n" +
            "  libpostproc    53.  3.100 / 53.  3.100\n" +
            "Input #0, mov,mp4,m4a,3gp,3g2,mj2, from 'VID_20150414_191241.mp4':\n" +
            "  Metadata:\n" +
            "    major_brand     : isom\n" +
            "    minor_version   : 0\n" +
            "    compatible_brands: isom3gp4\n" +
            "    creation_time   : 2015-04-14 11:12:45\n" +
            "    location        : +39.9947+116.4754/\n" +
            "    location-eng    : +39.9947+116.4754/\n" +
            "  Duration: 00:00:03.58, start: 0.000000, bitrate: 9178 kb/s\n" +
            "    Stream #0:0(eng): Video: h264 (Baseline) (avc1 / 0x31637661), yuv420p, 1280x720, 7842 kb/s, SAR 1:1 DAR 16:9, 29.18 fps, 29.25 tbr, 90k tbn, 180k tbc (default)\n" +
            "    Metadata:\n" +
            "      rotate          : 90\n" +
            "      creation_time   : 2015-04-14 11:12:45\n" +
            "      handler_name    : VideoHandle\n" +
            "    Side data:\n" +
            "      displaymatrix: rotation of -90.00 degrees\n" +
            "    Stream #0:1(eng): Audio: aac (LC) (mp4a / 0x6134706D), 48000 Hz, stereo, fltp, 97 kb/s (default)\n" +
            "    Metadata:\n" +
            "      creation_time   : 2015-04-14 11:12:45\n" +
            "      handler_name    : SoundHandle";
//    @Test
    public void regInfoTest(){
        VideoInfo vi  = FFmpegUtils.regInfo(stdout);
        log.debug(vi.toString());
    }

    @Test
    public void getRotateTest(){
        String str = "      rotate          : 90\n";
        String regexRotate = "rotate (.*?): (\\d*)(\\d*)";
        Pattern patternRotate = Pattern.compile(regexRotate);
        Matcher matcherRotate = patternRotate.matcher(str);
        if (matcherRotate.find()) {
            for (int i = 0; i < matcherRotate.groupCount(); i++) {
                if (log.isDebugEnabled()) {
                    log.debug("index {} : rotate:'{}'", i, matcherRotate.group(i));
                }
            }
//                String rotate = matcherRotate.group(0);
//                if (rotate.indexOf(":") > 0) {
//                    vi.setRotate(Integer.valueOf(rotate.substring(rotate.indexOf(":") + 1, rotate.length()).trim()));
//                }
        }
    }
}
