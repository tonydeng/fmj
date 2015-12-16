package com.github.tonydeng.fmj.handler;

import com.github.tonydeng.fmj.BaseTest;
import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by tonydeng on 15/5/12.
 */
@Ignore
public class ProcessCallbackHandlerTest extends BaseTest {
    private static InputStream input;
    private static long fileSize;
    private List<Integer> numbers = Lists.newArrayList(
            1, 10, 100, 1000, 10000, 100000, 1000000, 10000000
    );

    @Before
    public void init() throws FileNotFoundException {
        File f = new File("/Users/tonydeng/temp/api-top-book-name.log");
        input = new FileInputStream(f);
        fileSize = f.length();
    }

    @Test
    public void scannerHandlerTest() throws Exception {
        for (int number : numbers) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            for (int i = 0; i < number; i++) {
                Scanner scanner = new Scanner(input);
                StringBuffer sb = new StringBuffer();
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine()).append("\n");
                }
//            String result = handler.handler(input);
//            log.info("scanner handler  result:'{}'", result);
            }
            stopwatch.stop();
            if (log.isInfoEnabled()) {
                log.info("scannerHandlerTest file size '{}' run number : '{}' use time {} seconds, {} milliseconds",
                        fileSize, number,
                        stopwatch.elapsed(TimeUnit.SECONDS),
                        stopwatch.elapsed(TimeUnit.MILLISECONDS));
            }
        }

    }


    @Test
    public void defaultHandlerTest() throws Exception {
        DefaultCallbackHandler handler = new DefaultCallbackHandler();
        for (int number : numbers) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            for (int i = 0; i < number; i++) {
                handler.handler(input);
//            String result = handler.handler(input);
//            log.info("default handler  result:'{}'", result);
            }
            stopwatch.stop();
            if (log.isInfoEnabled()) {
                log.info("defaultHandlerTest file size '{}' run number : '{}' use time {} seconds, {} milliseconds",
                        fileSize, number,
                        stopwatch.elapsed(TimeUnit.SECONDS),
                        stopwatch.elapsed(TimeUnit.MILLISECONDS));
            }
        }
    }

    @Test
    public void byteStreamsTest() throws IOException {
        for (int number : numbers) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            for (int i = 0; i < number; i++) {
                new String(ByteStreams.toByteArray(input), Charsets.UTF_8);
//            log.info("byteStreamsTest  result:'{}'", result);
            }
            stopwatch.stop();
            if (log.isInfoEnabled()) {
                log.info("byteStreamsTest file size '{}' run number : '{}' use time {} seconds, {} milliseconds",
                        fileSize, number,
                        stopwatch.elapsed(TimeUnit.SECONDS),
                        stopwatch.elapsed(TimeUnit.MILLISECONDS));
            }
        }
    }

    @Test
    public void charStreamsTest() throws IOException {
        for (int number : numbers) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            for (int i = 0; i < number; i++) {
                new String(CharStreams.toString(new InputStreamReader(input)));
//            log.info("charStreamsTest  result:'{}'", result);
            }
            stopwatch.stop();
            if (log.isInfoEnabled()) {
                log.info("charStreamsTest file size '{}' run number : '{}' use time {} seconds, {} milliseconds",
                        fileSize, number,
                        stopwatch.elapsed(TimeUnit.SECONDS),
                        stopwatch.elapsed(TimeUnit.MILLISECONDS));
            }
        }
    }

    @Test
    public void ioUtilsTest() throws IOException {
        for (int number : numbers) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            for (int i = 0; i < number; i++) {
                IOUtils.toString(input, StandardCharsets.UTF_8);
//            log.info("ioUtilsTest  result:'{}'", result);
            }
            stopwatch.stop();
            if (log.isInfoEnabled()) {
                log.info("ioUtilsTest file size '{}' run number : '{}' use time {} seconds, {} milliseconds",
                        fileSize, number,
                        stopwatch.elapsed(TimeUnit.SECONDS),
                        stopwatch.elapsed(TimeUnit.MILLISECONDS));
            }
        }
    }
}
