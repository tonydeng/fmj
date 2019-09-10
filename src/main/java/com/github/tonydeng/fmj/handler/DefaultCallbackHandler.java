package com.github.tonydeng.fmj.handler;

import com.github.tonydeng.fmj.runner.BaseCommandOption;
import com.google.common.collect.Lists;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by tonydeng on 15/4/20.
 */
@Slf4j
public class DefaultCallbackHandler implements ProcessCallbackHandler {

    @Override
    public String handler(InputStream inputStream) throws IOException {
        @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, BaseCommandOption.UTF8));
        List<String> buffer = Lists.newArrayList();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.add(line);
        }
        return String.join("\n", buffer);
    }
}
