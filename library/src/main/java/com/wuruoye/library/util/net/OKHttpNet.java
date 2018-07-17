package com.wuruoye.library.util.net;

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
    public String get(String url, Map<String, String> values) throws WNetException {
        return request(url, values, METHOD.GET);
    }

    @Override
    public String post(String url, Map<String, String> values) throws WNetException {
        return request(url, values, METHOD.POST);
    }

    @Override
    public String request(String url, Map<String, String> values, METHOD method) throws WNetException {
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
            return request(builder.build());
        } catch (JSONException e) {
            throw new WNetException(e.getMessage());
        }
    }

    @Override
    public String downloadFile(String url, String file) throws WNetException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful()) {
                InputStream is = response.body().byteStream();
                boolean result = FileUtil.writeInputStream(file, is);
                if (result) {
                    return file;
                }else {
                    throw new WNetException("error in write file");
                }
            }else {
                throw new WNetException(response.message());
            }
        } catch (IOException e) {
            throw new WNetException(e.getMessage());
        }
    }

    @Override
    public String uploadFile(String url, Map<String, String> values, Map<String, String> files,
                             List<String> types) throws WNetException{
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
                throw new WNetException("file " + entry.getValue() + " is a directory not file");
            }else if (!file.exists()) {
                throw new WNetException("file " + entry.getValue() + " is not exists");
            }
            builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody
                    .create(MediaType.parse(types.get(i++)), file));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        return request(request);
    }

    private String request(Request request) throws WNetException {
        try {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }else {
                throw new WNetException(response.message());
            }
        } catch (IOException e) {
            throw new WNetException(e.getMessage());
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
