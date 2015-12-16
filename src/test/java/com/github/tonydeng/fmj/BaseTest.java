package com.github.tonydeng.fmj;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tonydeng on 15/4/16.
 */
public class BaseTest {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test(){
        log.debug("base test......");
    }
}
