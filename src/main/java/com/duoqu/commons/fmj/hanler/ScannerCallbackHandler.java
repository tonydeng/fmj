package com.duoqu.commons.fmj.hanler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by tonydeng on 15/5/12.
 */
public class ScannerCallbackHandler implements ProcessCallbackHandler {
    private static final Logger log =LoggerFactory.getLogger(ScannerCallbackHandler.class);
    public String handler(InputStream inputStream) throws Exception {

        Scanner scanner = new Scanner(inputStream);
        StringBuffer sb = new StringBuffer();
        while (scanner.hasNextLine()){
            sb.append(scanner.nextLine());
        }
        if(log.isDebugEnabled())
            log.debug(sb.toString());
        return sb.toString();
    }
}
