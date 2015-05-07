package com.duoqu.commons.fmj.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by tonydeng on 15/4/20.
 */
public class DefaultProcessCallbackHandler implements ProcessCallbackHandler{
    private  static final Logger log = LoggerFactory.getLogger(DefaultProcessCallbackHandler.class);

    private String result;
//    public String handler(InputStream errorStream) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream,BaseCommandOption.UTF8));
//        StringBuffer sb = new StringBuffer();
//        String line;
//        try {
//            while ((line = reader.readLine()) != null){
//                sb.append(line).append("\n");
//            }
//        }catch (IOException e){
//            log.error("read from process error : '{}'",e);
//            throw e;
//        }finally {
//            try {
//                errorStream.close();
//            }catch (IOException e){
//                log.error("close errorStream error: '{}'",e);
//            }
//        }
//        setResult(sb.toString());
//
//        return getResult();
//    }
public String handler(InputStream errorStream) throws IOException {
    FileChannel inputChannel =null;
    if (errorStream instanceof FileInputStream) {
        inputChannel = ((FileInputStream) errorStream).getChannel();
    } else{

        return "";
    }
    ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream(4096);
    ByteBuffer buffer = ByteBuffer.allocate(4096);
    StringBuffer sb = new StringBuffer();

    try {
        while (inputChannel.read(buffer) > -1) {
            buffer.flip();
            sb.append(buffer.get());
            buffer.clear();
        }
    } catch (IOException e) {
        // 当脚本执行超时，由于channel的关闭必然会抛出异常
        log.error("read from process error : '{}'",e);
        throw e;
    }
    finally {
        if(inputChannel!=null)
            inputChannel.close();
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
