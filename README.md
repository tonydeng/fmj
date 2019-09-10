# 项目介绍

[![Build Status](https://travis-ci.org/tonydeng/fmj.svg?branch=master)](https://travis-ci.org/tonydeng/fmj)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.tonydeng:fmj&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.tonydeng:fmj)<br/>
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.github.tonydeng:fmj&metric=coverage)](https://sonarcloud.io/dashboard?id=com.github.tonydeng:fmj)
[![Lines of code](https://sonarcloud.io/api/project_badges/measure?project=com.github.tonydeng:fmj&metric=ncloc)](https://sonarcloud.io/dashboard?id=com.github.tonydeng:fmj)
[![SonarCloud Bugs](https://sonarcloud.io/api/project_badges/measure?project=com.github.tonydeng:fmj&metric=bugs)](https://sonarcloud.io/project/issues?id=com.github.tonydeng:fmj&resolved=false&types=BUG)
[![SonarCloud vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=com.github.tonydeng:fmj&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=com.github.tonydeng:fmj)

- [项目介绍](#%e9%a1%b9%e7%9b%ae%e4%bb%8b%e7%bb%8d)
  - [FMJ (FFMpeg for Java)](#fmj-ffmpeg-for-java)
  - [FFMpeg安装](#ffmpeg%e5%ae%89%e8%a3%85)
    - [Linux](#linux)
    - [Mac](#mac)
    - [Windows](#windows)
  - [具体的ffmpeg命令可以参考下面的文档](#%e5%85%b7%e4%bd%93%e7%9a%84ffmpeg%e5%91%bd%e4%bb%a4%e5%8f%af%e4%bb%a5%e5%8f%82%e8%80%83%e4%b8%8b%e9%9d%a2%e7%9a%84%e6%96%87%e6%a1%a3)
  - [其他解释](#%e5%85%b6%e4%bb%96%e8%a7%a3%e9%87%8a)
    - [全金属被甲弹（FMJ - Full Metal Jacket）](#%e5%85%a8%e9%87%91%e5%b1%9e%e8%a2%ab%e7%94%b2%e5%bc%b9fmj---full-metal-jacket)

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
4. 打开命令行，执行 **ffmpeg -version**

## 具体的ffmpeg命令可以参考下面的文档

[FFMpeg命令介绍](https://github.com/tonydeng/fmj/blob/master/ffmpeg.md)

## 其他解释

### 全金属被甲弹（FMJ - Full Metal Jacket）

弹头为铅质或铅锑合金以提升比重与质量，然而铅质延展性过强以致于如果直接作为弹头发射，会于击发时碎裂或与枪管摩擦产生变形，最后与大气作不良的空气动力结合而失去弹道精准性。因此将铅为铜所完全包覆，使弹头能够承受击发时的推进力又不会磨损变形；然而较轻的比重与质量使得全金属包覆弹进入密度高的目标物，例如人体（人体密度为大气的1000倍），就会因为因惯性而产生的动能扩散于目标物上，以至于动能对目标物所产生的作用力结合入射角/反射角的效应而产生滚转。这个滚转为预期与期盼的效果，尽管子弹终端弹道的滚转不可预期，然而滚转的途径势必能够造成深层广泛的肌肉撕裂伤，甚至切断动脉击碎骨骼，而造成人员严重的伤害与死亡。
