package com.duoqu.commons.fmj.handler;

import com.duoqu.commons.fmj.runner.BaseCommandOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by tonydeng on 15/4/20.
 */
public class DefaultCallbackHandler implements ProcessCallbackHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultCallbackHandler.class);


    public String handler(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, BaseCommandOption.UTF8));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
