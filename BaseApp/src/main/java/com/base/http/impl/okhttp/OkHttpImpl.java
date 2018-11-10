package com.base.http.impl.okhttp;


import com.base.http.common.BaseHttpClient;
import com.base.http.common.HttpConfiguration;
import com.base.http.entity.METHOD;
import com.base.http.impl.BaseHttpImpl;
import com.base.http.interceptor.RetryWithDelay;
import com.base.http.log.okHttpLog.HttpLoggingInterceptorM;
import com.base.http.params.URLEncodedUtils;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * AsyncHttpClient async网络申请实现类
 * 如果有新网络tcp请求，就要重新实现一个网络交互类
 * <p>
 * OkHttpImpl such is the package okhttp network library independent implementation module,
 * in such a network data request.
 *
 * @author applehsp
 */


public class OkHttpImpl<T> implements BaseHttpImpl {

    //单例模式实现
    public OkHttpClient mOkHttpClient;
    public static OkHttpImpl instance;

    public static OkHttpImpl getOkClient(OkHttpClient okHttpClient) {
        if (instance == null) {
            synchronized (OkHttpImpl.class) {
                if (instance == null) {
                    instance = new OkHttpImpl(okHttpClient);
                }
            }
        } else {
            if (okHttpClient != null) {
                instance.mOkHttpClient = okHttpClient;
            }
        }
        return instance;
    }

    @Override
    public void stopExecute() {

    }


    @Override
    public void init(HttpConfiguration configuration) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(configuration.getConnectTimeout(), TimeUnit.SECONDS);
        builder.readTimeout(configuration.getReadTimeout(), TimeUnit.SECONDS);
        builder.writeTimeout(configuration.getWriteTimeout(), TimeUnit.SECONDS);

        if (configuration.getCacheDir() != null && configuration.getCacheDir().isDirectory()) {
            Cache cache = new Cache(configuration.getCacheDir(), configuration.getDiskCacheSize());
            builder.cache(cache);
        }
        if (configuration.getCookieJar() != null)
            builder.cookieJar(configuration.getCookieJar());

        if (configuration.getCertificates() != null)
            builder.sslSocketFactory(configuration.getCertificates());
        builder.retryOnConnectionFailure(configuration.isRetryOnConnectionFailure());
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        builder.protocols(Collections.singletonList(Protocol.HTTP_1_1));
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory newFactory = sc.getSocketFactory();
            builder.sslSocketFactory(newFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.addInterceptor(new RetryWithDelay(3));
        HttpLoggingInterceptorM interceptor = new HttpLoggingInterceptorM();
        interceptor.setLevel(HttpLoggingInterceptorM.Level.BODY);
        builder.addNetworkInterceptor(interceptor);
        mOkHttpClient = builder.build();
    }


    TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] x509Certificates,
                String s) throws java.security.cert.CertificateException {
        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] x509Certificates,
                String s) throws java.security.cert.CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    }};

    public static OkHttpImpl getOkClient() {
        if (instance == null) {
            synchronized (OkHttpImpl.class) {
                if (instance == null) {
                    instance = new OkHttpImpl(null);
                }
            }
        }
        return instance;
    }

    public OkHttpImpl(OkHttpClient okHttpClient) {
        if (okHttpClient != null) {
            mOkHttpClient = okHttpClient;
        }
    }


    public void get(BaseHttpClient client, Callback callback) {
        Call call = null;
        try {
            Request.Builder request;
            if (client.getTag() != null) {
                request = new Request.Builder()
                        .url(URLEncodedUtils.getUrlWithQueryString(client.isShouldEncodeUrl(), client.getUrl(), client.getParams())).tag(client.getTag())
                        .get();
            } else {
                request = new Request.Builder()
                        .url(URLEncodedUtils.getUrlWithQueryString(client.isShouldEncodeUrl(), client.getUrl(), client.getParams()))
                        .get();
            }
            for (ConcurrentHashMap.Entry<String, String> entry1 : client.getParams().headParams.entrySet()) {
                request.header(entry1.getKey(), entry1.getValue());
            }
//            if (baseObserver.retryWithDelay != null)
//                mOkHttpClient.newBuilder().addInterceptor(baseObserver.retryWithDelay);
            call = mOkHttpClient.newCall(request.build());
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void put(BaseHttpClient client, BaseFlowable<T> baseObserver) {
//        Call call = null;
//        try {
//            Request request;
//            RequestBody requestBody = new RequestBody() {
//                @Override
//                public MediaType contentType() {
//                    return null;
//                }
//
//                @Override
//                public void writeTo(BufferedSink sink) throws IOException {
//
//                }
//            };
//            if (client.getTag() != null) {
//                request = new Request.Builder()
//                        .url(client.getUrl()).tag(client.getTag())
//                        .put(requestBody).build();
//            } else {
//                request = new Request.Builder().tag(MD5Util.encrypt(client.getUrl()))
//                        .url(client.getUrl())
//                        .put(requestBody).build();
//            }
//            if (baseObserver.retryWithDelay != null)
//                mOkHttpClient.newBuilder().addInterceptor(baseObserver.retryWithDelay);
//            call = mOkHttpClient.newCall(request);
//            call.enqueue(baseObserver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void patch(BaseHttpClient client, BaseFlowable<T> baseObserver) {
//        Call call = null;
//        try {
//            Request request;
//            RequestBody requestBody = new RequestBody() {
//                @Override
//                public MediaType contentType() {
//                    return null;
//                }
//
//                @Override
//                public void writeTo(BufferedSink sink) throws IOException {
//
//                }
//            };
//            if (client.getTag() != null) {
//                request = new Request.Builder()
//                        .url(client.getUrl()).tag(client.getTag())
//                        .patch(requestBody).build();
//            } else {
//                request = new Request.Builder().tag(MD5Util.encrypt(client.getUrl()))
//                        .url(client.getUrl())
//                        .patch(requestBody).build();
//            }
//            if (baseObserver.retryWithDelay != null)
//                mOkHttpClient.newBuilder().addInterceptor(baseObserver.retryWithDelay);
//            call = mOkHttpClient.newCall(request);
//            call.enqueue(baseObserver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void delete(BaseHttpClient client, BaseFlowable<T> baseObserver) {
//        Call call = null;
//        try {
//            Request request;
//            if (client.getTag() != null) {
//                request = new Request.Builder()
//                        .url(URLEncodedUtils.getUrlWithQueryString(client.isShouldEncodeUrl(), client.getUrl(), client.getParams())).tag(client.getTag())
//                        .delete().build();
//            } else {
//                request = new Request.Builder().tag(MD5Util.encrypt(client.getUrl()))
//                        .url(URLEncodedUtils.getUrlWithQueryString(client.isShouldEncodeUrl(), client.getUrl(), client.getParams()))
//                        .delete().build();
//            }
//            if (baseObserver.retryWithDelay != null)
//                mOkHttpClient.newBuilder().addInterceptor(baseObserver.retryWithDelay);
//            call = mOkHttpClient.newCall(request);
//            call.enqueue(baseObserver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void postString(BaseHttpClient client, BaseFlowable<T> baseObserver) {
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), client.getContent());
//        Call call = null;
//        Request.Builder request = new Request.Builder()
//                .url(client.getUrl());
//        if (client.getTag() != null) {
//            request.tag(client.getTag());
//        }
//        if (client.getParams() != null) {
//            request.post(requestBody);
//            for (ConcurrentHashMap.Entry<String, String> entry : client.getParams().headParams.entrySet()) {
//                request.header(entry.getKey(), entry.getValue());
//            }
//        }
//        try {
//            if (baseObserver.retryWithDelay != null)
//                mOkHttpClient.newBuilder().addInterceptor(baseObserver.retryWithDelay);
//            mOkHttpClient.newCall(request.build()).enqueue(baseObserver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void postFile(BaseHttpClient client, BaseFlowable<T> baseObserver) {
//        MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
//        RequestBody fileBody = null;
//        Request.Builder request = new Request.Builder()
//                .url(client.getUrl());
//        if (client.getTag() != null) {
//            request.tag(client.getTag());
//        }
//        if (client.getParams() != null) {
//            if (client.getParams().fileParams.size() > 0) {
//                for (ConcurrentHashMap.Entry<String, BaseParams.FileWrapper> entry1 : client.getParams().fileParams.entrySet()) {
//                    {
//                        File file = entry1.getValue().file;
//                        fileBody = RequestBody.create(MEDIA_TYPE_STREAM, file);
//                    }
//                }
//            }
//            request.post(fileBody);
//            for (ConcurrentHashMap.Entry<String, String> entry : client.getParams().headParams.entrySet()) {
//                request.header(entry.getKey(), entry.getValue());
//            }
//        }
//        try {
//            if (baseObserver.retryWithDelay != null)
//                mOkHttpClient.newBuilder().addInterceptor(baseObserver.retryWithDelay);
//            mOkHttpClient.newCall(request.build()).enqueue(baseObserver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public void postForm(BaseHttpClient client, Callback callback) {
        RequestBody requestBody = null;
        // FormBody.Builder builder = new FormBody.Builder();
        if (client.getUrl().startsWith("null")) {
            return;
        }
        Request.Builder request = new Request.Builder()
                .url(client.getUrl());
        if (client.getTag() != null) {
            request.tag(client.getTag());
        }
        StringBuffer buffer = new StringBuffer();
        if (client.getParams() != null) {
            for (ConcurrentHashMap.Entry<String, String> entry : client.getParams().urlParams.entrySet()) {
                // builder.add(entry.getKey(), entry.getValue());
                buffer.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"),
                    buffer.toString());
            request.post(requestBody);
            for (ConcurrentHashMap.Entry<String, String> entry : client.getParams().headParams.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
            request.tag(client.getTag());
        }
        try {
            mOkHttpClient.newCall(request.build()).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//
//    public void postFormFile(BaseHttpClient client, BaseFlowable<T> baseObserver) {
//        RequestBody requestBody = null;
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        Request.Builder request = new Request.Builder()
//                .url(client.getUrl());
//        if (client.getTag() != null) {
//            request.tag(client.getTag());
//        }
//        if (client.getParams() != null) {
//            for (ConcurrentHashMap.Entry<String, String> entry : client.getParams().urlParams.entrySet()) {
//                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
//                        RequestBody.create(null, entry.getValue()));
//            }
//            requestBody = builder.build();
//            if (client.getParams().fileParams.size() > 0) {
//                for (ConcurrentHashMap.Entry<String, BaseParams.FileWrapper> entry1 : client.getParams().fileParams.entrySet()) {
//                    {
//                        File file = entry1.getValue().file;
//                        String fileName = file.getName();
//                        RequestBody fileBody = RequestBody.create(MediaType.parse(URLEncodedUtils.guessMimeType(fileName)), file);
//                        //根据文件名设置contentType
//                        builder.addFormDataPart(entry1.getKey(), fileName, fileBody);
//                    }
//                }
//            }
//            request.post(requestBody);
//            for (ConcurrentHashMap.Entry<String, String> entry : client.getParams().headParams.entrySet()) {
//                request.header(entry.getKey(), entry.getValue());
//            }
//        }
//        try {
//            //     BaseOkCall handler = new BaseOkCall(client,baseObserver);
//            mOkHttpClient.newCall(request.build()).enqueue(baseObserver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void downloadFile(BaseHttpClient client, BaseFlowable<T> baseFlowable) {
//        Call call = null;
//        try {
//            Request.Builder request = new Request.Builder()
//                    .url(URLEncodedUtils.getUrlWithQueryString(client.isShouldEncodeUrl(),
//                            client.getUrl(), client.getParams()));
//            if (client.getTag() != null) {
//                request.tag(client.getTag());
//            }
//            call = mOkHttpClient.newCall(request.get().build());
//            call.enqueue(baseFlowable);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void postFileProgress(BaseHttpClient client, BaseSubscriber<T> baseObserver) {
//        MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
//        RequestBody fileBody = null;
//        Request.Builder request = new Request.Builder()
//                .url(client.getUrl());
//        if (client.getTag() != null) {
//            request.tag(client.getTag());
//        }
//        if (client.getParams() != null) {
//            if (client.getParams().fileParams.size() > 0) {
//                for (ConcurrentHashMap.Entry<String, BaseParams.FileWrapper> entry1 : client.getParams().fileParams.entrySet()) {
//                    {
//                        File file = entry1.getValue().file;
//                        fileBody = RequestBody.create(MEDIA_TYPE_STREAM, file);
//                    }
//                }
//            }
//            request.post(new ProgressRequestBody(client, baseObserver, fileBody));
//            for (ConcurrentHashMap.Entry<String, String> entry : client.getParams().headParams.entrySet()) {
//                request.header(entry.getKey(), entry.getValue());
//            }
//        }
//        try {
//            BaseOkCall handler = new BaseOkCall(client, baseObserver);
//            mOkHttpClient.newCall(request.build()).enqueue(handler);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void postFormProgress(BaseHttpClient client, BaseSubscriber<T> baseObserver) {
//        RequestBody requestBody = null;
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//
//        Request.Builder request = new Request.Builder()
//                .url(client.getUrl());
//        if (client.getTag() != null) {
//            request.tag(client.getTag());
//        }
//        if (client.getParams() != null) {
//            for (ConcurrentHashMap.Entry<String, String> entry : client.getParams().urlParams.entrySet()) {
//                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
//                        RequestBody.create(null, entry.getValue()));
//            }
//            if (client.getParams().fileParams.size() > 0) {
//                for (ConcurrentHashMap.Entry<String, BaseParams.FileWrapper> entry1 : client.getParams().fileParams.entrySet()) {
//                    {
//                        File file = entry1.getValue().file;
//                        String fileName = file.getName();
//                        RequestBody fileBody = RequestBody.create(MediaType.parse(URLEncodedUtils.guessMimeType(fileName)), file);
//                        //TODO 根据文件名设置contentType
//                        builder.addFormDataPart(entry1.getKey(), fileName, fileBody);
//                    }
//                }
//            }
//            requestBody = builder.build();
//            request.post(new ProgressRequestBody(client, baseObserver, requestBody));
//            for (ConcurrentHashMap.Entry<String, String> entry : client.getParams().headParams.entrySet()) {
//                request.header(entry.getKey(), entry.getValue());
//            }
//        }
//        try {
//            BaseOkCall handler = new BaseOkCall(client, baseObserver);
//            mOkHttpClient.newCall(request.build()).enqueue(handler);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void execute(BaseHttpClient client, Callback callback) {
        switch (client.getMethod()) {
            case METHOD.GET:
            case METHOD.GET_FILE:
                get(client, callback);
                break;
            case METHOD.POST_STRING:
                //  postString(client, baseObserver);
                break;
            case METHOD.POST_FORM:
                postForm(client, callback);
                break;
            case METHOD.POST_FILE:
            case METHOD.POST_FILE_PROGRESS:
                //   postFileProgress(client,baseObserver);
                break;
            case METHOD.POST_FORM_FILE:
            case METHOD.POST_FORM_PROGRESS:
                ///postFormProgress(client,baseObserver);
                break;
            case METHOD.DOWNLOAD_FILE:
                //   downloadFile(client, baseObserver);
                break;
            case METHOD.PUT:
                //   put(client, baseObserver);
                break;
            case METHOD.PATCH:
                //  patch(client, baseObserver);
                break;
            case METHOD.DELETE:
                // delete(client, baseObserver);
                break;
        }
    }


}
