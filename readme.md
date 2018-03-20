# WBase

## 添加依赖

首先需要在项目`build.gradle`文件中加入：

```groovy
repositories {
        maven { url "https://jitpack.io" }
}
```

把上面的 maven 地址放到`build.gradle`的`buildscript`和`allprojects`目录下，然后在模块`build.gradle`文件中加入：

```groovy
implementation 'com.github.ruoyewu:wbase:0.0.1'
```

把这段放到`build.gradle`文件的`dependencies`目录下，`sync project`即可。

## 介绍

WBse 作为一个快速开发 Android 应用的基础包存在，里面包含很多已经写好的模版、基本工具等，避免每写一个应用就需要写很多类似的包的重复工作，大大降低开发成本。