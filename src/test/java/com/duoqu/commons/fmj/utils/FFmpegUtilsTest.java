package com.duoqu.commons.fmj.utils;

import com.duoqu.commons.fmj.BaseTest;
import org.junit.Test;

/**
 * Created by tonydeng on 15/4/17.
 */
public class FFmpegUtilsTest extends BaseTest {
    private static final String stdout = "ffmpeg version 2.6.1 Copyright (c) 2000-2015 the FFmpeg developers  built with Apple LLVM version 6.0 (clang-600.0.57) (based on LLVM 3.5svn)  configuration: --prefix=/usr/local/Cellar/ffmpeg/2.6.1 --enable-shared --enable-pthreads --enable-gpl --enable-version3 --enable-hardcoded-tables --enable-avresample --cc=clang --host-cflags= --host-ldflags= --enable-libx264 --enable-libmp3lame --enable-libvo-aacenc --enable-libxvid --enable-vda  libavutil      54. 20.100 / 54. 20.100  libavcodec     56. 26.100 / 56. 26.100  libavformat    56. 25.101 / 56. 25.101  libavdevice    56.  4.100 / 56.  4.100  libavfilter     5. 11.102 /  5. 11.102  libavresample   2.  1.  0 /  2.  1.  0  libswscale      3.  1.101 /  3.  1.101  libswresample   1.  1.100 /  1.  1.100  libpostproc    53.  3.100 / 53.  3.100Input #0, mov,mp4,m4a,3gp,3g2,mj2, from '/Users/tonydeng/temp/m3u8/VID_20150414_191241.mp4':  Metadata:    major_brand     : isom    minor_version   : 0    compatible_brands: isom3gp4    creation_time   : 2015-04-14 11:12:45    location        : +39.9947+116.4754/    location-eng    : +39.9947+116.4754/  Duration: 00:00:03.58, start: 0.000000, bitrate: 9178 kb/s    Stream #0:0(eng): Video: h264 (Baseline) (avc1 / 0x31637661), yuv420p, 1280x720, 7842 kb/s, SAR 1:1 DAR 16:9, 29.18 fps, 29.25 tbr, 90k tbn, 180k tbc (default)    Metadata:      rotate          : 90      creation_time   : 2015-04-14 11:12:45      handler_name    : VideoHandle    Side data:      displaymatrix: rotation of -90.00 degrees    Stream #0:1(eng): Audio: aac (LC) (mp4a / 0x6134706D), 48000 Hz, stereo, fltp, 97 kb/s (default)    Metadata:      creation_time   : 2015-04-14 11:12:45      handler_name    : SoundHandleAt least one output file must be specified";

    @Test
    public void regInfoTest(){
        FFmpegUtils.regInfo(stdout);
    }
}
