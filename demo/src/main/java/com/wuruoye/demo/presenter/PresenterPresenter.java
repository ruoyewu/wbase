package com.wuruoye.demo.presenter;

import com.wuruoye.demo.contract.PresenterContract;
import com.wuruoye.library.model.WConfig;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public class PresenterPresenter extends PresenterContract.Presenter {

    @Override
    public void request(String num) {
        try {
            final int n = Integer.parseInt(num);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i <= n; i++) {
                        final int finalI = i;
                        WConfig.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isAvailable()) {
                                    getView().onResult("round " + finalI);
                                }
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (NumberFormatException e) {
            if (isAvailable()) {
                getView().onResult("error number");
            }
        }
    }
}
