package com.adasplus.proadas.common.util.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.adasplus.proadas.App;
import com.adasplus.proadas.common.util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author: fengyin
 * Time: 16-9-23 14:40.
 * Email: fengyin@adasplus.com
 * Description: Network reqeust manager include get and post request.
 * Also include download files from server or upload files to server.
 */

public class RequestManager {
    private static RequestManager sRequestManager;
    private OkHttpClient mOkHttpClient;
    private Context mContext;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    private RequestManager(Context context) {
        mContext = context;
        //  Cache cache = new Cache(new File(context.getCacheDir(), "HttpCache"), 1024 * 1024 * 16);
        mOkHttpClient = new OkHttpClient.Builder().
                connectTimeout(1, TimeUnit.SECONDS).
                writeTimeout(30, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS).
                retryOnConnectionFailure(true).
                cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<HttpUrl, List<Cookie>>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                }).build();
    }

    public static RequestManager getInstance(Context context) {
        if (sRequestManager == null) {
            synchronized (RequestManager.class) {
                if (sRequestManager == null) {
                    sRequestManager = new RequestManager(context);
                }
            }
        }
        return sRequestManager;
    }

    public void loadImage(String url, final ImageListener listener) {
        Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int ret = -1;
                while ((ret = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, ret);
                }
                baos.flush();
                byte[] data = baos.toByteArray();
                listener.onSuccess(data);
                baos.close();
            }
        });

        //  call.cancel();
    }


    /**
     * Get origin result from response.
     *
     * @param url
     * @param listener
     * @param params
     */
    public void getResponseByGetMethod(String url, final IReponseListener<String> listener,
                                       Map<String, String> params) {

        final Call call = getCallByGetParams(url, params);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (call.isCanceled()) {
                    Log.e("okHttp", "cancle");
                    return;
                }
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail("请求失败");
                    }
                });

                Log.e("okHttp", "ok3 get request failure ::  " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result = response.body().string();
                Log.e("okHttp", "ok3 get request success ::  " + result);
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(result);
                    }
                }, 500);
            }
        });
    }

    public void getReponseByPostMethod(final String url, final IReponseListener<String> listener,
                                       final Map<String, String> params) {

        Call call = getCallByPostParams(url, params);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (call.isCanceled()) {
                    return;
                }
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail("请求失败");
                    }
                });
                Log.e("okHttp", "ok3 post request failure ::  " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result = response.body().string();
                Log.e("okHttp", "post success ::   " + result);
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(result);
                    }
                }, 1000);

            }
        });
    }

    public void getReponseByPostMethodDe(final String url, final IReponseListener<String> listener,
                                       final Map<String, String> params) {

        Call call = getCallByPostParams(url, params);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (call.isCanceled()) {
                    return;
                }
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFail("请求失败");
                    }
                });
                Log.e("okHttp", "ok3 post request failure ::  " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result = response.body().string();
                Log.e("okHttp", "post success ::   " + result);
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(result);
                    }
                }, 0);

            }
        });
    }

    public String getReponseByPostMethod(String url,
                                         Map<String, String> params) {
        Call call = getCallByPostParams(url, params);

        try {
            Response response = call.execute();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Request buildMutlipartFormRequest(String url, Map<String, File> fileParams, Map<String, String> stringParams) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (stringParams != null && stringParams.size() > 0) {
            for (Map.Entry<String, String> entry : stringParams.entrySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                        RequestBody.create(null, entry.getValue()));
            }
        }


        if (fileParams != null && fileParams.size() > 0) {
            RequestBody fileBody = null;
            for (Map.Entry<String, File> entry : fileParams.entrySet()) {
                File file = entry.getValue();
                fileBody = RequestBody.create(MediaType.parse(getMimeType(file.getName())), file);
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + entry.getKey() + "\"; filename=\"" + file.getName() + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    private String getMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        return contentTypeFor == null ? "application/octet-stream" : contentTypeFor;
    }

    private Call getCallByPostParams(String url, Map<String, String> params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            Set<Map.Entry<String, String>> set = params.entrySet();
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody formBody = formBodyBuilder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        return mOkHttpClient.newCall(request);
    }

    private Call getCallByGetParams(String url, Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        if (params != null && params.size() > 0) {
            Set<Map.Entry<String, String>> set = params.entrySet();
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            sb.append("?");
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                sb.append(entry.getKey()).
                        append("=").append(entry.getValue()).append("&");
            }
        }

        Request request = new Request.Builder().url(sb.toString()).build();
        return mOkHttpClient.newCall(request);
    }

    public interface ImageListener {
        void onSuccess(byte[] data);

        void onError();
    }

    public void cancleAll() {
        if (mOkHttpClient != null) {
            mOkHttpClient.dispatcher().cancelAll();
        }
    }
}