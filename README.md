# Android-CU 使用说明
---
## 简介
CU是clear unused的缩写，本项目主要用来清理Android工程中无用的代码文件和资源文件。

`CURes.java`用于清理资源文件，借助于ADT SDK自带的lint工具，相对路径为\sdk\tools\lint.bat。

`CUSrc.java`用于清理.java文件，需要Eclipse插件[UCDetector](http://www.ucdetector.org)配合。


## 使用

清除无用文件，需要交替运行`CURes.java`和`CUSrc.java`，直到没有可删除文件为止。

### 运行CURes.java
- 运行参数为lint.bat文件绝对路径和android工程目录，如 `D:adt/sdk/tools/lint.bat  D:/nova`。
- `String[] dirArray`为要删除资源文件的相对目录，默认为res目录下。一般来说，`values`不需要删除，故不添加。
- 运行结果保存在当前目录下，文件名为格式化后的时间戳。

### 运行CUSrc.java
- 设置UCDetector，忽略不需要扫描的文件，如Activity
- 使用UCDetector扫描项目生成txt报告
- 运行程序需要两个参数，UCDetector生成的报告路径，项目的路径，如
	`D:/UCDetector/report.txt	D:/nova`
- 运行结果会保存在当前目录下的UnusedJava.txt文件中。

## 注意
- 清除资源时，如果使用字符串形式调用layout等资源文件，无法被lint识别，会造成误删。
- 清除代码时，如果使用字符串形式调用fragment等控件或者使用反射时，无法被UCDetector识别，会造成误删。


