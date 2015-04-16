package com.duoqu.commons.fmj.model;

/**
 * Created by tonydeng on 15/4/16.
 */
public class VideoInfo {
    private String format;
    private long size;
    private long duration;
    private VideoResolution resolution;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public VideoResolution getResolution() {
        return resolution;
    }

    public void setResolution(VideoResolution resolution) {
        this.resolution = resolution;
    }


    @Override
    public String toString() {
        return "VideoInfo{" +
                "format='" + format + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", resolution=" + resolution +
                '}';
    }
}
