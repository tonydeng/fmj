# FFMPEG 使用说明

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
-hls_wrap 设置多少片之后开始覆盖，如果设置为0则不会覆盖，默认值为0。这个选项能够避免在磁盘上存储过多的偏，而且能够限制写入磁盘的最多片的数量。
```
注意，播放列表的sequence number对每个segment来说都必须是唯一的，而且它不能和片的文件名（当使用wrap选项时，文件名可能会重复使用）混淆。

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