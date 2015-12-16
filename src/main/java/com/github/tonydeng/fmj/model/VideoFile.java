package com.github.tonydeng.fmj.model;

import java.io.File;

/**
 * Created by tonydeng on 15/4/22.
 */
public class VideoFile {
    private File input;
    private File target;
    private VideoInfo inputInfo;
    private VideoInfo targetInfo;
    private boolean isSuccess;
    public VideoFile(){}
    public VideoFile(File input,File target){
        setInput(input);
        setTarget(target);
    }
    public File getInput() {
        return input;
    }

    public void setInput(File input) {
        this.input = input;
    }

    public File getTarget() {
        return target;
    }

    public void setTarget(File target) {
        this.target = target;
    }

    public VideoInfo getInputInfo() {
        return inputInfo;
    }

    public void setInputInfo(VideoInfo inputInfo) {
        this.inputInfo = inputInfo;
    }

    public VideoInfo getTargetInfo() {
        return targetInfo;
    }

    public void setTargetInfo(VideoInfo targetInfo) {
        this.targetInfo = targetInfo;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Override
    public String toString() {
        return "VideoFile{" +
                "input=" + input.getAbsolutePath() +
                ", target=" + target.getAbsolutePath() +
                ", inputInfo=" + inputInfo +
                ", targetInfo=" + targetInfo +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
