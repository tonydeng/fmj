package com.github.tonydeng.fmj.model;

/**
 * Created by tonydeng on 15/4/15.
 */
public class VideoResolution {

    private int width;
    private int height;

    public VideoResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "VideoResolution{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
