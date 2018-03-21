package com.wuruoye.library.util.net;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.wuruoye.library.model.Listener;
import com.wuruoye.library.model.WConfig;
import com.wuruoye.library.util.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wuruoye on 2018/3/20.
 * this file is to
 */

public class OKHttpNet implements IWNet {
    private PARAM_TYPE mType = PARAM_TYPE.FORM;
    private static List<Cookie> mCookies;
    private static OkHttpClient mClient;

    public OKHttpNet() {
        mCookies = new ArrayList<>();
        mClient = new OkHttpClient.Builder()
                .connectTimeout(WConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NonNull HttpUrl url,
                                                 @NonNull List<Cookie> cookies) {
                        mCookies.clear();
                        if (mCookies != null) {
                            mCookies.addAll(cookies);
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
                        return mCookies;
                    }
                })
                .build();
    }

    @Override
    public void setParamType(PARAM_TYPE type) {
        mType = type;
    }

    @Override
    public void get(String url, ArrayMap<String, String> values, final Listener<String> listener) {
        StringBuilder builder = new StringBuilder(url);
        if (values.size() > 0) {
            builder.append("?");
        }
        for (Map.Entry<String, String> entry : values.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        url = builder.toString();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        request(request, listener);
    }

    @Override
    public void post(String url, ArrayMap<String, String> values, final Listener<String> listener) {
        try {
            Request.Builder builder = new Request.Builder().url(url);
            if (mType == PARAM_TYPE.FORM) {
                builder.post(map2form(values));
            }else if (mType == PARAM_TYPE.JSON) {
                RequestBody body = RequestBody.create(MediaType.parse("json"), map2json(values));
                builder.post(body);
            }
            request(builder.build(), listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadFile(String url, String key, String file, String type,
                           final Listener<String> listener) {
        File f = new File(file);
        if (f.isDirectory()) {
            listener.onFail("file " + file + " is a directory not file");
        }else if (!f.exists()) {
            listener.onFail("file " + file + " is not exists");
        }else {
            RequestBody body = new MultipartBody.Builder()
                    .addFormDataPart(key, f.getName(), RequestBody.create(MediaType.parse(type), f))
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            request(request, listener);
        }
    }

    @Override
    public void downloadFile(String url, final String file, final Listener<String> listener) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        mClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onFail(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            InputStream is = response.body().byteStream();
                            boolean result = FileUtil.writeInputStream(file, is);
                            if (result) {
                                listener.onSuccessful(file);
                            }else {
                                listener.onFail("error in write file");
                            }
                        }else {
                            listener.onFail(response.message());
                        }
                    }
                });
    }

    private void request(Request request, final Listener<String> listener) {
        mClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onFail(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            listener.onSuccessful(response.body().string());
                        }else {
                            listener.onFail(response.message());
                        }
                    }
                });
    }

    private String map2json(ArrayMap<String, String> values) throws JSONException {
        JSONObject object = new JSONObject();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            object.put(entry.getKey(), entry.getValue());
        }
        return object.toString();
    }

    private FormBody map2form(ArrayMap<String, String> values) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
