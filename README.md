# AndroidDisk

包含项目描述，参考文献，项目计划，会议总结，项目进展等内容。

---

- [项目描述](#项目描述)
  1. [设计背景](#1-设计背景)
  2. [设计目标（功能描述）](#2-设计目标功能描述)
  3. [关键技术](#3-关键技术)
     1. [手机端app设计](#31-手机端app设计)
     2. [pc端小程序设计](#32-pc端小程序设计)
- [项目计划](#项目计划)
- [会议总结](#会议总结)
  1. [10/26 第一次会议](#1026-第一次会议)
  2. [10/30 第二次会议](#1030-第二次会议)
- [工作流程](#工作流程)
  - [11/15 工作总结](#1115-工作总结)
  - [11/30 工作总结](#1130-工作总结)

---


## 项目描述

#### 1. 设计背景

- 随着智能手机的发展，手机移动端在人们生活中显得日渐重要。而出于娱乐、办公等方面的需要，手机端文件与PC端文件互相传输交流也成为了刚需。

- 对于手机访问PC端的共享文件，市面上已有较为成熟的解决方案（以Android手机为例）

  PC端设置文件共享+Android端安装`ES文件浏览器`

- 而PC端访问手机文件时，市面上也有一些解决方案，如MIUI自带的基于FTP的远程管理、ES文件浏览器的远程管理等。这些方案都有或多或少的弊端。

  |        解决方案        | <center>弊端</center>                                        |
  | :--------------------: | ------------------------------------------------------------ |
  |   MIUI自带的远程管理   | 1. 必须使用小米手机<br>2. 无法设置特定的文件夹进行共享<br>3. PC端需要手动输入FTP地址 |
  | ES文件浏览器的远程管理 | 1. APP集成度较高，功能众多，APP体量大<br>2. 无法使用用户名口令登录<br>3. 无法设置特定的文件夹进行共享<br>4. PC端需要手动输入FTP地址 |
  |    其他文件管理方案    | 需要手机端与PC端同时安装软件                                 |

#### 2. 设计目标（功能描述）

　　项目力求将手机端（Android系统）中的文件系统扩展作为PC端（Windows系统）的虚拟硬盘。

1. 设计解决方案，实现下述需求

    1. 在连接到同一个局域网时，手机端的文件系统自动显示在PC端文件资源管理器中，并作为一个网络位置（虚拟硬盘）可以方便的对文件进行增删改查。

    2. 另外提升用户体验，在后台运行手机端的应用与PC端的程序而无需用户介入，在连接同一个局域网后自动适配。
2. 基本思路
   1. 设计一款APP（AndroidDisk），实现当安卓手机与PC连在同一个局域网时，PC端能够便捷的使用windows自带的文件资源管理器管理安卓手机中的共享文件。
   2. 设计PC端的小程序，不断扫描所在局域网FTP端口，发现相应设备并确认身份后自动添加网络位置（虚拟网盘）。

1. 基本界面

   |                        Android端                         |                           PC端                            |
   | :------------------------------------------------------: | :-------------------------------------------------------: |
   | <img src="README_images/基本界面1.png" style="zoom:10%"> | <img src="README_images/基本界面2.png" style="zoom:200%"> |

2. 具体功能

   - 用户口令验证
   - 设置特定的共享文件
   - PC端扫描局域网，确认设备后自动添加

#### 3. 关键技术

##### 3.1 手机端app设计

1. APP实现在Android端搭建FTP服务器

   对比[apache ftp server](https://projects.apache.org/project.html?mina-ftpserver)与[swiftp](https://code.google.com/archive/p/swiftp/)的优劣再进行取舍

2. 设计相关UI交互实现登录口令设置与特定文件共享

##### 3.2 PC端小程序设计

- PC端使用talnet不断确认手机端口状态，并验证口令，添加网络位置
- 基本思路：确认端口状态 --> 验证口令 --> ftp连接 --> 添加网络位置到资源管理器

## 项目计划

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;预计18年11月正式开始毕设，本学期最后一周（1月17日前）完成。

| <center>时间段</center> | <center>计划内容</center>                                    |
| ----------------------- | ------------------------------------------------------------ |
| ~11月15日               | 查阅相关资料，比较两种方案，并选定其中一个进行毕设设计       |
| 11月15日~11月30日       | 写出基本的demo（实现FTP连接功能），并调试debug完成           |
| 11月30日~12月15日       | 进一步完善demo功能（口令登录、共享特定文件夹），并调试debug完成 |
| 12月15日~12月30日       | 设计PC端程序                                                 |
| 12月30日~1月15日        | 设计PC端程序                                                 |


## 会议总结

#### 10/26 第一次会议

- 参考老师给定的第一个题目，`在手机中设定一个文件夹，当手机和电脑处于同一个局域网时，电脑中会出现一个文件夹`，查阅了相关资料，决定实现一个基于ad-hoc网络的文件共享系统
- 会议上导师的看法
  1. 手机需要root，并连接PC使用adb进行配置ad hoc网络模式，没有实际使用意义
  2. ad hoc网络研究热潮在十年前，由于运营商等方面的原因，认为其没有发展前景
- 于是放弃该想法，由于构想不成熟被毙，我根据构想找的论文也没有作用，所以决定于下次会议重新pre两篇论文

#### 10/30 第二次会议

- 由于家中有事，缺席第二次会议

## 工作流程

#### 11/15 工作总结

- 根据导师的建议，增添了相应的需求：PC端自动连接功能。并对应修改了[项目描述](#项目描述)与[项目计划](#项目计划)
- 第一阶段的工作任务较为简单，通过搜索相关资料，发现`apache ftp server`相较于`swiftp`相关资源较多，实现起来较为简单，于是最终敲定使用`apache ftp server`完成毕业设计
- 辞去工作时间996的实习工作，以便能更投入毕业设计
- 接下来两周我将把具有基本功能的完整app编写debug完毕

#### 11/30 工作总结



