package com.duoqu.commons.fmj.utils;

import com.duoqu.commons.fmj.BaseTest;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created by tonydeng on 15/4/17.
 */
public class FileUtilsTest extends BaseTest {
//    @Test
    public void createDirectoryTest() {
        List<String> paths = Lists.newArrayList(
                "/Users/tonydeng/temp/1",
                "/Users/tonydeng/temp/1/1",
                "/Users/tonydeng/temp/1/2/2",
                "/opt/1"
        );

        for (String path : paths) {
            log.info("create dir :'{}' status:'{}'", path, FileUtils.createDirectory(path));
        }
    }

//    @Test
    public void getM3U8OutputByInputTest() {
        List<File> inputs = Lists.newArrayList(
                new File("/Users/tonydeng/temp/m3u8/muse_va_v97.mp4"),
                new File("/Users/tonydeng/temp/m3u8/muse_va_v140.mp4")
        );

        for (File input : inputs) {
            log.info("input:'{}'   meu8:'{}'", input.getAbsolutePath(), FileUtils.getM3U8OutputByInput(input));
        }
    }
    @Test
    public void findTSTest(){
        File m3u8 = new File("/Users/tonydeng/temp/m3u8/v100/m3u8/muse_va_v100.m3u8");
        List<File> ts = FileUtils.findTS(m3u8);
        for(File t:ts){
            log.info(t.getAbsolutePath());
        }
    }
}
