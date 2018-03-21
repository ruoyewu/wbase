package com.wuruoye.demo.contract;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public interface PhotoContract {
    interface View extends WIView {
        void onDeleteError(String path);
    }

    abstract class Presenter extends WPresenter<View> {
        public abstract String getPath();
        public abstract void deleteFile(List<String> fileList);
    }
}
