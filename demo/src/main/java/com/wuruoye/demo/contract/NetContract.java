package com.wuruoye.demo.contract;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public interface NetContract {
    interface View extends WIView {
        void onResult(String result);
    }

    abstract class Presenter extends WPresenter<View> {
        public abstract void request(String url);
    }
}
