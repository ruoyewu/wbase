package com.wuruoye.library.util.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @Created : wuruoye
 * @Date : 2018/4/30 20:20.
 * @Description :
 */

public class DefaultThreadPool implements WIThreadPool {

    private ScheduledThreadPoolExecutor mExecutor;

    public DefaultThreadPool() {
        mExecutor = new ScheduledThreadPoolExecutor(5);
    }

    @Override
    public void exec(Runnable runnable) throws Exception{
        mExecutor.submit(runnable);
    }

    @Override
    public <T> T exec(Callable<T> callable) throws Exception {
        return mExecutor.submit(callable).get();
    }
}
