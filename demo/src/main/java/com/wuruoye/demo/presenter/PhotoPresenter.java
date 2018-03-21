package com.wuruoye.demo.presenter;

import com.wuruoye.demo.contract.PhotoContract;
import com.wuruoye.library.model.WConfig;
import com.wuruoye.library.util.FileUtil;

import java.util.List;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public class PhotoPresenter extends PhotoContract.Presenter {
    @Override
    public String getPath() {
        return WConfig.IMAGE_PATH + System.currentTimeMillis() + ".jpg";
    }

    @Override
    public void deleteFile(List<String> fileList) {
        for (String path : fileList) {
            boolean result = FileUtil.deleteFile(path);
            if (!result && isAvailable()) {
                getView().onDeleteError(path);
            }
        }
    }
}
