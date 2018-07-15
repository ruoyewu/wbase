package com.wuruoye.library.util.net;

import android.support.annotation.NonNull;

import com.wuruoye.library.model.Listener;
import com.wuruoye.library.model.WConfig;
import com.wuruoye.library.util.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wuruoye on 2018/3/20.
 * 默认网络请求类
 */

public class OKHttpNet implements IWNet {
    private PARAM_TYPE mType = PARAM_TYPE.FORM;
    private static OkHttpClient mClient;

    public OKHttpNet() {
        mClient = new OkHttpClient.Builder()
                .connectTimeout(WConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpClient getClient() {
        return mClient;
    }

    public static void setClient(OkHttpClient client) {
        mClient = client;
    }

    @Override
    public void setParamType(PARAM_TYPE type) {
        mType = type;
    }

    @Override
    public void get(String url, Map<String, String> values, final Listener<String> listener) {
        request(url, values, listener, METHOD.GET);
    }

    @Override
    public void post(String url, Map<String, String> values, final Listener<String> listener) {
        request(url, values, listener, METHOD.POST);
    }

    @Override
    public void request(String url, Map<String, String> values, Listener<String> listener,
                        METHOD method) {
        String m = method.name();
        try {
            if (method == METHOD.GET) {
                url = generateUrl(url, values);
            }
            Request.Builder builder = new Request.Builder().url(url);
            switch (method) {
                case GET:
                    builder.get();
                    break;
                case HEAD:
                    builder.head();
                    break;
                default:
                    builder.method(m, generateForm(values));
            }
            request(builder.build(), listener);
        } catch (JSONException e) {
            e.printStackTrace();
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
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        listener.onFail(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response)
                            throws IOException {
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

    @Override
    public void uploadFile(String url, Map<String, String> values, Map<String,
            String> files, List<String> types, Listener<String> listener) {
        if (files.size() != types.size()) {
            throw new IllegalArgumentException();
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        int i = 0;
        for (Map.Entry<String, String> entry : files.entrySet()) {
            File file = new File(entry.getValue());
            if (file.isDirectory()) {
                listener.onFail("file " + entry.getValue() + " is a directory not file");
                return;
            }else if (!file.exists()) {
                listener.onFail("file " + entry.getValue() + " is not exists");
                return;
            }
            builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody
                    .create(MediaType.parse(types.get(i++)), file));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        request(request, listener);
    }

    private void request(Request request, final Listener<String> listener) {
        try {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful()) {
                listener.onSuccessful(response.body().string());
            }else {
                listener.onFail(response.message());
            }
        } catch (IOException e) {
            listener.onFail(e.getMessage());
        }
    }

    private String generateUrl(String url, Map<String, String> values) {
        StringBuilder builder = new StringBuilder(url);
        builder.append("?");
        for (Map.Entry<String, String> entry : values.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        return builder.toString();
    }

    private RequestBody generateForm(Map<String, String> values, PARAM_TYPE type)
            throws JSONException {
        switch (type) {
            case FORM:
                FormBody.Builder builder = new FormBody.Builder();
                for (Map.Entry<String, String> entry : values.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
                return builder.build();
            case JSON:
                JSONObject obj = new JSONObject();
                for (Map.Entry<String, String> entry : values.entrySet()) {
                    obj.put(entry.getKey(), entry.getValue());
                }
                return RequestBody.create(MediaType.parse("json"), obj.toString());
        }

        throw new IllegalArgumentException("unable handler type " + type);
    }

    private RequestBody generateForm(Map<String, String> values) throws JSONException {
        return generateForm(values, mType);
    }
}
