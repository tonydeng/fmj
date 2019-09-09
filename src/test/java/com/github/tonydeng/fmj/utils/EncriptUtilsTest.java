package com.github.tonydeng.fmj.utils;

import com.github.tonydeng.fmj.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tony Deng
 * @version V1.0
 * @date 2019-09-10 00:28
 **/
@Slf4j
public class EncriptUtilsTest extends BaseTest {

    private static final String INPUT = "12345";

    @Test
   public void testMd5() {
        Assert.assertEquals("827CCB0EEA8A706C4C34A16891F84E7B",EncriptUtils.md5(INPUT));
    }
}
