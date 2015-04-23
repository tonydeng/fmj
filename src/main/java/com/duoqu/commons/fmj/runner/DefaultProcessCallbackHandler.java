package com.duoqu.commons.fmj.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by tonydeng on 15/4/20.
 */
public class DefaultProcessCallbackHandler implements ProcessCallbackHandler{
    private  static final Logger log = LoggerFactory.getLogger(DefaultProcessCallbackHandler.class);

    private String result;
    public String handler(InputStream errorStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream,BaseCommandOption.UTF8));
        StringBuffer sb = new StringBuffer();
        String line;
        try {
            while ((line = reader.readLine()) != null){
                sb.append(line).append("\n");
//                if(log.isDebugEnabled())
//                    log.debug(line);
            }
        }catch (IOException e){
            log.error("read from process error : '{}'",e);
            throw e;
        }finally {
            try {
                errorStream.close();
            }catch (IOException e){
                log.error("close errorStream error: '{}'",e);
            }
        }
        setResult(sb.toString());

        return getResult();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
