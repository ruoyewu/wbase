package com.wuruoye.library.util.net;


import com.wuruoye.library.model.Listener;
import com.wuruoye.library.model.WConfig;
import com.wuruoye.library.util.thread.WThreadPool;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Created by wuruoye on 2018/3/20.
 * 网络请求入口类
 */

public class WNet {
    private static IWNet mNet;

    public static void init(IWNet net) {
        mNet = net;
    }

    public static <T> T getNet() {
        return (T)mNet;
    }

    public static void setType(IWNet.PARAM_TYPE type) {
        mNet.setParamType(type);
    }

    public static void get(String url, Map<String, String> values, Listener<String> listener) {
        mNet.get(url, values, listener);
    }

    public static void post(String url, Map<String, String> values, Listener<String> listener) {
        mNet.post(url, values, listener);
    }

    public static void request(String url, Map<String, String> values, Listener<String> listener,
                               IWNet.METHOD method) {
        mNet.request(url, values, listener, method);
    }

    public static void uploadFile(String url, String key, String file, String type,
                                  Listener<String> listener) {
        uploadFile(url, Collections.<String, String>emptyMap(), Collections.singletonMap(key, file),
                Collections.singletonList(type), listener);
    }

    public static void uploadFile(String url, Map<String, String> values, Map<String,
            String> files, List<String> types, Listener<String> listener) {
        mNet.uploadFile(url, values, files, types, listener);
    }

    public static void getInBackground(final String url, final Map<String, String> values,
                                       final Listener<String> listener) {
        try {
            WThreadPool.exec(new Runnable() {
                @Override
                public void run() {
                    get(url, values, new Listener<String>() {
                        @Override
                        public void onSuccessful(final String result) {
                            WConfig.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onSuccessful(result);
                                }
                            });
                        }

                        @Override
                        public void onFail(final String message) {
                            WConfig.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onFail(message);
                                }
                            });
                        }
                    });
                }
            });
        } catch (Exception e) {
            listener.onFail("fail in WThreadPool : " + e.getMessage());
        }
    }

    public static void postInBackground(final String url, final Map<String, String> values,
                                        final Listener<String> listener) {
        try {
            WThreadPool.exec(new Runnable() {
                @Override
                public void run() {
                    post(url, values, new Listener<String>() {
                        @Override
                        public void onSuccessful(final String result) {
                            WConfig.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onSuccessful(result);
                                }
                            });
                        }

                        @Override
                        public void onFail(final String message) {
                            WConfig.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onFail(message);
                                }
                            });
                        }
                    });
                }
            });
        } catch (Exception e) {
            listener.onFail("fail in WThreadPool : " + e.getMessage());
        }
    }

    public static void requestInBackground(final String url, final Map<String, String> values,
                                           final Listener<String>
                                           listener, final IWNet.METHOD method) {
        try {
            WThreadPool.exec(new Runnable() {
                @Override
                public void run() {
                    request(url, values, new Listener<String>() {
                        @Override
                        public void onSuccessful(final String result) {
                            WConfig.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onSuccessful(result);
                                }
                            });
                        }

                        @Override
                        public void onFail(final String message) {
                            WConfig.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onFail(message);
                                }
                            });
                        }
                    }, method);
                }
            });
        } catch (Exception e) {
            listener.onFail("fail in WThreadPool : " + e.getMessage());
        }
    }

    public static void uploadFileInBackground(final String url, final String key, final String file,
                                              final String type, final Listener<String> listener) {
        try {
            WThreadPool.exec(new Runnable() {
                @Override
                public void run() {
                    uploadFile(url, key, file, type, new Listener<String>() {
                        @Override
                        public void onSuccessful(final String result) {
                            WConfig.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onSuccessful(result);
                                }
                            });
                        }

                        @Override
                        public void onFail(final String message) {
                            WConfig.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onFail(message);
                                }
                            });
                        }
                    });
                }
            });
        } catch (Exception e) {
            listener.onFail("fail in WThreadPool : " + e.getMessage());
        }
    }

    public static void uploadFileInBackground(final String url, final Map<String, String> values,
                                              final Map<String, String> files, final List<String> types,
                                              final Listener<String> listener) {
        try {
            WThreadPool.exec(new Runnable() {
                @Override
                public void run() {
                    uploadFile(url, values, files, types, new Listener<String>() {
                        @Override
                        public void onSuccessful(final String result) {
                            WConfig.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onSuccessful(result);
                                }
                            });
                        }

                        @Override
                        public void onFail(final String message) {
                            WConfig.runOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onFail(message);
                                }
                            });
                        }
                    });
                }
            });
        } catch (Exception e) {
            listener.onFail("fail in WThreadPool : " + e.getMessage());
        }
    }
}
