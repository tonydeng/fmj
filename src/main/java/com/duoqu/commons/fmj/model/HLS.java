package com.duoqu.commons.fmj.model;

import java.io.File;
import java.util.List;

/**
 * Created by tonydeng on 15/4/17.
 */
public class HLS {
    private File m3u8;
    private List<File> ts;

    public File getM3u8() {
        return m3u8;
    }

    public void setM3u8(File m3u8) {
        this.m3u8 = m3u8;
    }

    public List<File> getTs() {
        return ts;
    }

    public void setTs(List<File> ts) {
        this.ts = ts;
    }
}
