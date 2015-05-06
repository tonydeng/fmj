# 项目介绍

## FMJ (FFMpeg for Java)

![FMJ Logo](fmj.jpg)

通过Java调用FFMpeg命令的方式来对音视频进行处理（获取信息、截图等等）。


## FFMpeg安装
[FFMpeg官网](http://ffmpeg.org/)

建议使用 **ffmpeg-2.6.1** 版本

### Linux

`yum install ffmpeg`

`apt-get install ffmpeg`

### Mac

`brew install ffmpeg`

### Windows

1. 可以在[这儿](http://ffmpeg.zeranoe.com/builds/)下载编译好的FFmpeg
2. 解压到 **/path/to/ffmpeg** 
3. 添加 **/path/to/ffmpeg/bin** 到你的环境变量 **PATH** 中。
4. 打开命令行，执行 **ffmpeg -v**

## 具体的ffmpeg命令可以参考下面的文档

[FFMpeg命令介绍](ffmpeg.md)

## 其他解释

### 全金属被甲弹（FMJ - Full Metal Jacket）

弹头为铅质或铅锑合金以提升比重与质量，然而铅质延展性过强以致于如果直接作为弹头发射，会于击发时碎裂或与枪管摩擦产生变形，最后与大气作不良的空气动力结合而失去弹道精准性。因此将铅为铜所完全包覆，使弹头能够承受击发时的推进力又不会磨损变形；然而较轻的比重与质量使得全金属包覆弹进入密度高的目标物，例如人体（人体密度为大气的1000倍），就会因为因惯性而产生的动能扩散于目标物上，以至于动能对目标物所产生的作用力结合入射角/反射角的效应而产生滚转。这个滚转为预期与期盼的效果，尽管子弹终端弹道的滚转不可预期，然而滚转的途径势必能够造成深层广泛的肌肉撕裂伤，甚至切断动脉击碎骨骼，而造成人员严重的伤害与死亡。