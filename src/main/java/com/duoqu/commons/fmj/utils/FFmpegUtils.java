package com.duoqu.commons.fmj.utils;

import com.duoqu.commons.fmj.model.VideoInfo;
import com.duoqu.commons.fmj.model.VideoResolution;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tonydeng on 15/4/15.
 */
public class FFmpegUtils {
    private static final String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
    private static final String regexVideo = "Video: (.*?), (.*?), (.*?)[,\\s]";
    private static final String regexAudio = "Audio: (\\w*), (\\d*) Hz";

    /**
     *
     * @param commamdLine
     * @return
     */
    public static VideoInfo regInfo(String stdout) {
        if(StringUtils.isNotEmpty(stdout)){
            VideoInfo vi = new VideoInfo();
            Pattern patternDuration = Pattern.compile(regexDuration);
            Matcher matcherDuration = patternDuration.matcher(stdout);
            if (matcherDuration.find()) {
                String duration = matcherDuration.group(1);
                if(StringUtils.isNotBlank(duration) && duration.indexOf(":") >= 0){
                    String[] time = duration.split(":");
                    int hour = Integer.valueOf(time[0]);
                    int minute = Integer.valueOf(time[1]);
                    int second = 0;
                    if(time[2].indexOf(".") >= 0){
                        second = Integer.valueOf(time[2].substring(0,time[2].indexOf(".")));
                    }else{
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
                if(null != wh && wh.length == 2){
                    vi.setResolution(new VideoResolution(Integer.valueOf(wh[0]), Integer.valueOf(wh[1])));
                }
                vi.setFormat(matcherVideo.group(1));
            }
//            Pattern patternAudio = Pattern.compile(regexAudio);
//            Matcher matcherAudio = patternAudio.matcher(info);
//
//            if (matcherAudio.find()) {
////                map.put("音频编码", matcherAudio.group(1));
////                map.put("音频采样频率", matcherAudio.group(2));
//                ai.setDecoder(matcherAudio.group(1));
//            }
            return vi;
        }
        return null;
    }
}
