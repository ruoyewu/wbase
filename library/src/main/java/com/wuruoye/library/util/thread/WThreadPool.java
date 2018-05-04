package com.wuruoye.library.util.thread;

import com.wuruoye.library.model.Listener;
import com.wuruoye.library.ui.WBaseApp;

import java.util.concurrent.Callable;

/**
 * @Created : wuruoye
 * @Date : 2018/4/30 20:44.
 * @Description :
 */

public class WThreadPool {
    private static WIThreadPool sPool;

    private WThreadPool() {}

    public static void init(WIThreadPool pool) {
        sPool = pool;
    }

    public static void exec(Runnable runnable) throws Exception {
        sPool.exec(runnable);
    }

    public static <T> T exec(Callable<T> callable) throws Exception {
        return sPool.exec(callable);
    }

    public static <T> void execInBackground(final Callable<T> callable, final Listener<T> listener) {
        try {
            exec(new Runnable() {
                @Override
                public void run() {
                    try {
                        final T result = sPool.exec(callable);
                        WBaseApp.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccessful(result);
                            }
                        });
                    } catch (final Exception e) {
                        WBaseApp.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFail(e.getMessage());
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            listener.onFail("fail in WThreadPool : " + e.getMessage());
        }
    }
}
