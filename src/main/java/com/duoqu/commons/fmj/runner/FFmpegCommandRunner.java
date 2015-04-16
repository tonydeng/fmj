package com.duoqu.commons.fmj.runner;

import com.duoqu.commons.fmj.model.VideoInfo;
import com.duoqu.commons.fmj.utils.FFmpegUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Created by tonydeng on 15/4/16.
 */
public class FFmpegCommandRunner {
    private static final Logger log = LoggerFactory.getLogger(FFmpegCommandRunner.class);


    public static Process pro = null;

    private volatile static boolean isRunning = false;

    public static VideoInfo getMultmediaInfo(File input) {
        if (input != null && input.exists()) {
            List<String> commands = Lists.newArrayList();
            commands.addAll(BaseCommandOption.toCommonsCmdArrays(input.getAbsolutePath()));
            try {
                ProcessBuilder pb = new ProcessBuilder();
                pb.command(commands);

                pb.redirectErrorStream(true);


                Process p = pb.start();
                BufferedReader buf = null;
                buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = buf.readLine()) != null) {
                    sb.append(line);
//                System.out.println(line);
                    continue;
                }
//            System.out.println();

                int ret = p.waitFor();
                VideoInfo mi = FFmpegUtils.regInfo(sb.toString());
                mi.setSize(new FileInputStream(input).available());
                return mi;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
