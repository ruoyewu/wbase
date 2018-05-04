package com.wuruoye.library.util.thread;

import java.util.concurrent.Callable;

/**
 * @Created : wuruoye
 * @Date : 2018/4/30 20:14.
 * @Description :
 */

public interface WIThreadPool {
    void exec(Runnable runnable) throws Exception;

    <T> T exec(Callable<T> callable) throws Exception;
}