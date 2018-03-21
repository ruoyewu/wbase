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
implementation 'com.github.ruoyewu:wbase:0.0.3'
```

把这段放到`build.gradle`文件的`dependencies`目录下，`sync project`即可。

## 介绍

WBse 作为一个快速开发 Android 应用的基础包存在，里面包含很多已经写好的模版、基本工具等，避免每写一个应用就需要写很多类似的包的重复工作，大大降低开发成本。

### MVP

```java
interface DemoContract {
    interface View extends WIView {
        // receive result
        void onResult(Object result);
    }
    
    abstract class Presenter extends WPresenter<View> {
        // request data
        public abstract void requestData(Object params);
    }
}
```

对于一个 Activity ，首先定义这个 Activity 对应的 Contract 以确定 View 层和 Presenter 层的工作，然后使 Activity 实现这里的 View 接口，另外构建一个 DemoPresenter 继承这里的 Presenter 并实现对应的虚拟方法，并通过`getView()`得到 View 实例，并调用其方法`onResult(Object)`将结果返回到 View 层。

```java
public class DemoPresenter extends DemoContract.Presenter {
    @Override
    public void requestData(Object params) {
        Object result;
        if (isAvailable()) {
            getView().onResult(result);
        }
    }
}
```



```java
public class MainActivity extends WBaseActivity<DemoContract.Presenter> implements DemoContract.View {
    @Override
    public int getContentView() {
        // set xml view for this activity
        return R.layout.activity_main;
    }
    
    @Override
    public void initData(Bundle bundle) {
        // init data
        setPresenter(new DemoPresenter());
    }
    
    @Override
    public void initView() {
        // init views
        mPresenter.requestData(null);
    }
    
    @Override
    public void onResult(Object object) {
        // when get the result
    }
}
```

