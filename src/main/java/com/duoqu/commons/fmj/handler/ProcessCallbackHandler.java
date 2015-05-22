package com.duoqu.commons.fmj.handler;

import java.io.InputStream;

/**
 * Created by tonydeng on 15/4/20.
 */
public interface ProcessCallbackHandler {
    String handler(InputStream inputStream) throws Exception;
}
