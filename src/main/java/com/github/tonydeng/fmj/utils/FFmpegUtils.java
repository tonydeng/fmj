package com.github.tonydeng.fmj.utils;

import com.github.tonydeng.fmj.model.VideoInfo;
import com.github.tonydeng.fmj.model.VideoResolution;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tonydeng on 15/4/15.
 */
public class FFmpegUtils {
    private static final Logger log = LoggerFactory.getLogger(FFmpegUtils.class);
    private static final String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
    private static final String regexVideo = "Video: (.*?), (.*?), (.*?)[,\\s]";
    private static final String regexAudio = "Audio: (\\w*), (\\d*) Hz";
    private static final String regexRotate = "rotate (.*?): (\\d*)(\\d*)";

    public static VideoInfo regInfo(String stdout) {
        return regInfo(stdout, null);
    }

    /**
     * @param stdout
     * @return
     */
    public static VideoInfo regInfo(String stdout, VideoInfo vi) {
        if (StringUtils.isNotEmpty(stdout)) {
            if (vi == null) {
                vi = new VideoInfo();
            }

            Pattern patternDuration = Pattern.compile(regexDuration);
            Matcher matcherDuration = patternDuration.matcher(stdout);
            if (matcherDuration.find()) {
                String duration = matcherDuration.group(1);
                if (StringUtils.isNotBlank(duration) && duration.indexOf(":") >= 0) {
                    String[] time = duration.split(":");
                    int hour = Integer.valueOf(time[0]);
                    int minute = Integer.valueOf(time[1]);
                    int second = 0;
                    if (time[2].indexOf(".") >= 0) {
                        second = Integer.valueOf(time[2].substring(0, time[2].indexOf(".")));
                    } else {
                        second = Integer.valueOf(time[2]);
                    }
                    vi.setDuration((hour * 60 * 60) + (minute * 60) + second);
                }

//                map.put("提取出播放时间", matcherDuration.group(1));
//                map.put("开始时间", matcherDuration.group(2));
//                map.put("bitrate 码率 单位 kb", matcherDuration.group(3));
            }

            Pattern patternVideo = Pattern.compile(regexVideo);
            Matcher matcherVideo = patternVideo.matcher(stdout);

            if (matcherVideo.find()) {
//                map.put("编码格式", matcherVideo.group(1));
//                map.put("视频格式", matcherVideo.group(2));
//                map.put("分辨率", matcherVideo.group(3));
                String[] wh = matcherVideo.group(3).split("x");
                if (null != wh && wh.length == 2) {
                    vi.setResolution(new VideoResolution(Integer.valueOf(wh[0]), Integer.valueOf(wh[1])));
                }
                String format = matcherVideo.group(1);
                if (StringUtils.isNotBlank(format)) {
                    vi.setFormat(format.split(" ")[0]);
                }

            }
//            Pattern patternAudio = Pattern.compile(regexAudio);
//            Matcher matcherAudio = patternAudio.matcher(info);
//
//            if (matcherAudio.find()) {
////                map.put("音频编码", matcherAudio.group(1));
////                map.put("音频采样频率", matcherAudio.group(2));
//                ai.setDecoder(matcherAudio.group(1));
//            }

            Pattern patternRotate = Pattern.compile(regexRotate);
            Matcher matcherRotate = patternRotate.matcher(stdout);
            if (matcherRotate.find()) {
//                for (int i = 0; i < matcherRotate.groupCount(); i++) {
//                    if (log.isDebugEnabled()) {
//                        log.debug("index {} : rotate:'{}'", i, matcherRotate.group(i));
//                    }
//                }
                String rotate = matcherRotate.group(2);
                if (StringUtils.isNumeric(rotate)) {
                    vi.setRotate(Integer.valueOf(rotate));
                }
            }
            return vi;
        }
        return null;
    }


    /**
     * 构建ffmpeg命令
     *
     * @param commands
     * @return
     */
    public static String ffmpegCmdLine(List<String> commands) {
        StringBuffer sb = new StringBuffer();
//        for (String command : commands) {
//            sb.append(command + " ");
//        }
        commands.forEach((command) -> sb.append(command + " "));
        return sb.toString();
    }

}
