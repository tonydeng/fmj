package com.duoqu.commons.fmj.handler;

import com.duoqu.commons.fmj.BaseTest;
import com.google.common.base.Stopwatch;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by tonydeng on 15/5/12.
 */
public class ProcessCallbackHandlerTest extends BaseTest {
    private static InputStream input;
    private static final int number = 10000;

    @Before
    public void init() throws FileNotFoundException {
        input = new FileInputStream(new File("/Users/tonydeng/temp/api-top-book-name.log"));
    }

    @Test
    public void scannerHandlerTest() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ScannerCallbackHandler handler = new ScannerCallbackHandler();
        for (int i = 0; i < number; i++) {
            handler.handler(input);
//            String result = handler.handler(input);
//            log.info("scanner handler  result:'{}'", result);
        }
        stopwatch.stop();
        if (log.isInfoEnabled()) {
            log.info("scanner handler run {} seconds, {} milliseconds",
                    stopwatch.elapsed(TimeUnit.SECONDS),
                    stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }


    @Test
    public void defaultHandlerTest() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        DefaultCallbackHandler handler = new DefaultCallbackHandler();
        for (int i = 0; i < number; i++) {
            handler.handler(input);
//            String result = handler.handler(input);
//            log.info("default handler  result:'{}'", result);
        }
        stopwatch.stop();
        if (log.isInfoEnabled()) {
            log.info("default handler run {} seconds, {} milliseconds",
                    stopwatch.elapsed(TimeUnit.SECONDS),
                    stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }

}
