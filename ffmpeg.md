# FFMPEG 使用说明

## 截图命令

### 截取一张352x240尺寸大小，格式为jpg的图片
```
ffmpeg -i input_file -y -f image2 -t 0.001 -s 352x240 output.jpg
```

### 把视频的前30帧转换成一个Animated Gif
```
ffmpeg -i input_file -vframes 30 -y -f gif output.gif
```

### 在视频的第8.01秒出截取230x240的缩略图
```
ffmpeg -i input_file -y -f mjpeg -ss 8 -t 0.001 -s 320x240 output.jpg
```

### 每隔一秒截一张图

```
ffmpeg -i out.mp4 -f image2 -vf fps=fps=1 out%d.png
```

### 每隔20秒截一张图

```
ffmpeg -i out.mp4 -f image2 -vf fps=fps=1/20 out%d.png
```

### 多张截图合并到一个文件里（2x3）每隔一千帧(秒数=1000/fps25)即40s截一张图

```
ffmpeg -i out.mp4 -frames 3 -vf "select=not(mod(n\,1000)),scale=320:240,tile=2x3" out.png
```

### 从视频中生成GIF图片
```
ffmpeg -i out.mp4 -t 10 -pix_fmt rgb24 out.gif
```
### 转换视频为图片（每帧一张图）
```
ffmpeg -i out.mp4 out%4d.png
```

### 图片转换为视频
```
ffmpeg -f image2 -i out%4d.png -r 25 video.mp4
```

## 切分视频并生成M3U8文件

```
ffmpeg -i input.mp4 -c:v libx264 -c:a aac -strict -2 -f hls -hls_time 20 -hls_list_size 0 -hls_wrap 0 output.m3u8
```

相关参数说明：

```
-i 输入视频文件
-c:v 输出视频格式
-c:a 输出音频格式
-strict
-f hls 输出视频为HTTP Live Stream（M3U8）
-hls_time 设置每片的长度，默认为2，单位为秒
-hls_list_size 设置播放列表保存的最多条目，设置为0会保存所有信息，默认为5
-hls_wrap 设置多少片之后开始覆盖，如果设置为0则不会覆盖，默认值为0。这个选项能够避免在磁盘上存储过多的片，而且能够限制写入磁盘的最多片的数量。
```
注意，播放列表的sequence number对每个segment来说都必须是唯一的，而且它不能和片的文件名（当使用wrap选项时，文件名可能会重复使用）混淆。

## 分离视频音频流

```
ffmpeg -i input_file -vcodec copy -an output_file_video    //分离视频流
ffmpeg -i input_file -acodec copy -vn output_file_audio    //分离音频流
```

## 视频解复用

```
ffmpeg -i test.mp4 -vcoder copy -an -f m4v test.264
ffmpeg -i test.avi -vcoder copy -an -f m4v test.264
```

## 视频转码

```
ffmpeg -i test.mp4 -vcoder h264 -s 352*278 -an -f m4v test.264    //转码为码流原始文件
ffmpeg -i test.mp4 -vcoder h264 -bf 0 -g 25 -s 352-278 -an -f m4v test.264    //转码为码流原始文件
ffmpeg -i test.avi -vcoder mpeg4 -vtag xvid -qsame test_xvid.avi    //转码为封装文件 -bf B帧数目控制, -g 关键帧间隔控制, -s 分辨率控制
```
## 视频封装

```
ffmpeg -i video_file -i audio_file -vcoder copy -acodec copy output_file
```

## 视频剪切

```
ffmpeg -i test.avi -r 1 -f image2 image.jpeg //视频截图
ffmpeg -i input.avi -ss 0:1:30 -t 0:0:20 -vcoder copy -acoder copy output.avi //剪切视频 -r 提取图像频率， -ss 开始时间， -t 持续时间
```

## 视频录制

```
ffmpeg -i rtsp://hostname/test -vcoder copy out.avi
```

## YUV序列播放

```
ffplay -f rawvideo -video_size 1920x1080 input.yuv
```

## YUV序列转AVI

```
ffmpeg -s w*h -pix_fmt yuv420p -i input.yuv -vcoder mpeg4 output.avi
```

### 常用参数说明

#### 主要参数
```
-i 设定输入流
-f 设定输出格式
-ss 开始时间
```
#### 视频参数
```
-b 设定视频流量，默认是200Kbit/s
-s 设定画面的宽和高
-aspect 设定画面的比例
-vn 不处理视频
-vcoder 设定视频的编码器，未设定时则使用与输入流相同的编解码器
```
### 音频参数
```
-ar 设定采样率
-ac 设定声音的Channel数
-acodec 设定沈阳的Channel数
-an 不处理音频
```

## 使用ffmpeg合并MP4文件

```
ffmpeg -i "Apache Sqoop Tutorial Part 1.mp4" -c copy -bsf:v h264_mp4toannexb -f mpegts intermediate1.ts
ffmpeg -i "Apache Sqoop Tutorial Part 2.mp4" -c copy -bsf:v h264_mp4toannexb -f mpegts intermediate2.ts
ffmpeg -i "Apache Sqoop Tutorial Part 3.mp4" -c copy -bsf:v h264_mp4toannexb -f mpegts intermediate3.ts
ffmpeg -i "Apache Sqoop Tutorial Part 4.mp4" -c copy -bsf:v h264_mp4toannexb -f mpegts intermediate4.ts
ffmpeg -i "concat:intermediate1.ts|intermediate2.ts|intermediate3.ts|intermediate4.ts" -c copy -bsf:a aac_adtstoasc "Apache Sqoop Tutorial.mp4"
```

## 使用ffmpeg转换flv到mp4

```
ffmpeg -i out.flv -vcodec copy -acodec copy out.mp4
```

## 视频添加水印

### 水印局中

```
ffmpeg -i out.mp4 -i sxyx2008@163.com.gif -filter_complex overlay="(main_w/2)-(overlay_w/2):(main_h/2)-(overlay_h)/2" output.mp4
```

参数解释

* -i out.mp4(视频源)
* -i sxyx2008@163.com.gif(水印图片)
* overlay 水印的位置
* output.mp4 输出文件

## 视频翻转和旋转

### 翻转

#### 水平翻转语法: -vf hflip

```
ffplay -i out.mp4 -vf hflip
```

#### 垂直翻转语法：-vf vflip

```
ffplay -i out.mp4 -vf vflip
```

### 旋转

语法：transpose={0,1,2,3}

* 0:逆时针旋转90°然后垂直翻转

* 1:顺时针旋转90°

* 2:逆时针旋转90°

* 3:顺时针旋转90°然后水平翻转

### 将视频顺时针旋转90度

```
ffplay -i out.mp4 -vf transpose=1
```

### 将视频水平翻转(左右翻转)

```
ffplay -i out.mp4 -vf hflip
```

### 顺时针旋转90度并水平翻转

```
ffplay -i out.mp4 -vf transpose=1,hflip
```

## 添加字幕

有的时候你需要给视频加一个字幕(subtitle)，使用ffmpeg也可以做。一般我们见到的字幕以srt字幕为主，在ffmpeg里需要首先将srt字幕转化为ass字幕，然后就可以集成到视频中了(不是单独的字幕流，而是直接改写视频流)。

```
ffmpeg -i my_subtitle.srt my_subtitle.ass
ffmpeg -i inputfile.mp4 -vf ass=my_subtitle.ass outputfile.mp4
```

但是值得注意的是：

> my_subtitle.srt需要使用UTF8编码，老外不会注意到这一点，但是中文这是必须要考虑的；

将字幕直接写入视频流需要将每个字符渲染到画面上，因此有一个字体的问题，在ass文件中会指定一个缺省字体，例如Arial，但是我们首先需要让ffmpeg能找到字体文件，不然文字的渲染就无从谈起了。ffmpeg使用了fontconfig来设置字体配置。你需要首先设置一下FONTCONFIG_PATH或者FONTCONFIG_FILE环境变量，不然fontconfig是无法找到配置文件的，这一点请参看这篇文章，如果你设置的是FONTCONFIG_PATH，那把配置文件保存为%FONTCONFIG_PATH%/font.conf即可，然后你可以在font.conf文件中配置字体文件的路径之类的。

Windows下为fontconfig设置如下的环境变量

```
FC_CONFIG_DIR=C:\ffmpeg
FONTCONFIG_FILE=font.conf
FONTCONFIG_PATH=C:\ffmpeg
PATH=C:\ffmpeg\bin;%PATH%
```

下面是一个简单的Windows版`font.conf`文件。

```xml
<?xml version="1.0"?>
<fontconfig>

<dir>C:\WINDOWS\Fonts</dir>

<match target="pattern">
   <test qual="any" name="family"><string>mono</string></test>
   <edit name="family" mode="assign"><string>monospace</string></edit>
</match>

<match target="pattern">
   <test qual="all" name="family" mode="not_eq"><string>sans-serif</string></test>
   <test qual="all" name="family" mode="not_eq"><string>serif</string></test>
   <test qual="all" name="family" mode="not_eq"><string>monospace</string></test>
   <edit name="family" mode="append_last"><string>sans-serif</string></edit>
</match>

<alias>
   <family>Times</family>
   <prefer><family>Times New Roman</family></prefer>
   <default><family>serif</family></default>
</alias>
<alias>
   <family>Helvetica</family>
   <prefer><family>Arial</family></prefer>
   <default><family>sans</family></default>
</alias>
<alias>
   <family>Courier</family>
   <prefer><family>Courier New</family></prefer>
   <default><family>monospace</family></default>
</alias>
<alias>
   <family>serif</family>
   <prefer><family>Times New Roman</family></prefer>
</alias>
<alias>
   <family>sans</family>
   <prefer><family>Arial</family></prefer>
</alias>
<alias>
   <family>monospace</family>
   <prefer><family>Andale Mono</family></prefer>
</alias>
<match target="pattern">
   <test name="family" mode="eq">
      <string>Courier New</string>
   </test>
   <edit name="family" mode="prepend">
      <string>monospace</string>
   </edit>
</match>
<match target="pattern">
   <test name="family" mode="eq">
      <string>Courier</string>
   </test>
   <edit name="family" mode="prepend">
      <string>monospace</string>
   </edit>
</match>

</fontconfig>
```

下面这个是Linux系统下改版过来的

```xml
<?xml version="1.0"?>
<!DOCTYPE fontconfig SYSTEM "fonts.dtd">
<!-- /etc/fonts/fonts.conf file to configure system font access -->
<fontconfig>
<!-- 
    Find fonts in these directories
-->
<dir>C:/Windows/Fonts</dir>
<!--
<dir>/usr/X11R6/lib/X11/fonts</dir>
-->
<!--
    Accept deprecated 'mono' alias, replacing it with 'monospace'
-->
<match target="pattern">
    <test qual="any" name="family"><string>mono</string></test>
    <edit name="family" mode="assign"><string>monospace</string></edit>
</match>

<!--
    Load per-user customization file, but don't complain
    if it doesn't exist
-->
<include ignore_missing="yes" prefix="xdg">fontconfig/fonts.conf</include>

<!--
    Load local customization files, but don't complain
    if there aren't any
-->
<include ignore_missing="yes">conf.d</include>
<include ignore_missing="yes">local.conf</include>

<!--
    Alias well known font names to available TrueType fonts.
    These substitute TrueType faces for similar Type1
    faces to improve screen appearance.
-->
<alias>
    <family>Times</family>
    <prefer><family>Times New Roman</family></prefer>
    <default><family>serif</family></default>
</alias>
<alias>
    <family>Helvetica</family>
    <prefer><family>Arial</family></prefer>
    <default><family>sans</family></default>
</alias>
<alias>
    <family>Courier</family>
    <prefer><family>Courier New</family></prefer>
    <default><family>monospace</family></default>
</alias>

<!--
    Provide required aliases for standard names
    Do these after the users configuration file so that
    any aliases there are used preferentially
-->
<alias>
    <family>serif</family>
    <prefer><family>Times New Roman</family></prefer>
</alias>
<alias>
    <family>sans</family>
    <prefer><family>Arial</family></prefer>
</alias>
<alias>
    <family>monospace</family>
    <prefer><family>Andale Mono</family></prefer>
</alias>
</fontconfig>
```

参考：

> http://blog.raphaelzhang.com/2013/04/video-streaming-and-ffmpeg-transcoding/

## 嵌入字幕

在一个MP4文件里面添加字幕，不是把 .srt 字幕文件集成到 MP4 文件里，而是在播放器里选择字幕，这种集成字幕比较简单，速度也相当快

```
ffmpeg -i input.mp4 -i subtitles.srt -c:s mov_text -c:v copy -c:a copy output.mp4
```

希望字幕直接显示出来，其实也不难

```
ffmpeg -i subtitle.srt subtitle.ass
ffmpeg -i input.mp4 -vf ass=subtitle.ass output.mp4
```
参考：

> http://blog.neten.de/posts/2013/10/06/use-ffmpeg-to-burn-subtitles-into-the-video/