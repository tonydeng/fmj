package com.duoqu.commons.fmj.com.duoqu.commons.fmj.javaav;

import com.duoqu.commons.fmj.BaseTest;
import com.github.hoary.javaav.*;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by cc on 2015/5/13.
 */
public class JAVAAVTest extends BaseTest {


    @Test
    /****
     *从视频文件中，切图
     * */
    public void getImgFromVideo() throws JavaAVException, IOException {
        String video = "d:\\temp\\test.mp4";
        Demuxer demuxer = new Demuxer();
        demuxer.open(video);

        MediaFrame mediaFrame;
        int filecount=1;
        File file2 =null;
        while ( (mediaFrame = demuxer.readFrame()) != null) {
            if (mediaFrame.getType() == MediaFrame.Type.VIDEO) {
                VideoFrame videoFrame = (VideoFrame) mediaFrame;

                BufferedImage bi= Image.createImage(videoFrame, BufferedImage.TYPE_3BYTE_BGR);

                //Thread.sleep((long) (1000 / (demuxer.getFrameRate())));
                file2 = new File("d:\\temp\\"+filecount +  ".png");
                ImageIO.write(bi, "bmp", file2);
            }
            if (mediaFrame.getType() == MediaFrame.Type.AUDIO) {
                //AudioFrame audioFrame = (AudioFrame) mediaFrame;
                //Audio.getAudio16(audioFrame);
            }
            filecount++;
            if(filecount>100)
                break;
        }

        demuxer.close();
    }
//    public static void main(String[] args) throws Exception {
//        String video = "d:\\temp\\test.mp4";
//        Demuxer demuxer = new Demuxer();
////		CanvasFrame frame = new CanvasFrame("Demuxer Test: " + video);
////		frame.setSize(640, 480);
////		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
////		frame.addWindowListener(new WindowAdapter() {
////			@Override
////			public void windowClosing(WindowEvent e) {
////				reading = false;
////			}
////		});
//
//
//        demuxer.open(video);
//
//        MediaFrame mediaFrame;
//        int filecount=1;
//        File file2 =null;
//        while (reading && (mediaFrame = demuxer.readFrame()) != null) {
//            if (mediaFrame.getType() == MediaFrame.Type.VIDEO) {
//                VideoFrame videoFrame = (VideoFrame) mediaFrame;
//
//                BufferedImage bi= Image.createImage(videoFrame, BufferedImage.TYPE_3BYTE_BGR);
//
//                //Thread.sleep((long) (1000 / (demuxer.getFrameRate())));
//                file2 = new File("d:\\temp\\"+filecount +  ".png");
//                ImageIO.write(bi, "bmp", file2);
//            }
//            if (mediaFrame.getType() == MediaFrame.Type.AUDIO) {
//                //AudioFrame audioFrame = (AudioFrame) mediaFrame;
//                //Audio.getAudio16(audioFrame);
//            }
//            filecount++;
//            if(filecount>100)
//                break;
//        }
//
//        demuxer.close();
//    }

}
