package com.duoqu.commons.fmj.utils;

import com.duoqu.commons.fmj.BaseTest;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * Created by tonydeng on 15/4/17.
 */
public class FileUtilsTest extends BaseTest {
    @Test
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
}
