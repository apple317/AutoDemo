package com.base.http.impl.okhttp;


import com.base.http.common.HttpConfiguration;
import com.base.http.gsonfactory.CustomGsonConverterFactory;
import com.base.http.impl.BaseHttpImpl;
import com.base.http.interceptor.Networknterceptor;
import com.base.http.log.HttpLoggingInterceptor;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * AsyncHttpClient async网络申请实现类
 * 如果有新网络tcp请求，就要重新实现一个网络交互类
 * <p>
 * OkHttpImpl such is the package okhttp network library independent implementation module,
 * in such a network data request.
 *
 * @author applehsp
 */


public class OkHttpImpl implements BaseHttpImpl{

    //单例模式实现
    public OkHttpClient mOkHttpClient;
    public static OkHttpImpl instance;


    public Retrofit retrofit;

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
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.interceptors().add(loggingInterceptor);
        builder.networkInterceptors().add(new Networknterceptor());
        mOkHttpClient = builder.build();
        retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(configuration.getBaseUrl())
                .build();
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


    /**
     * 创建公共api对象
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createApi(final Class<T> service) {
        return retrofit.create(service);
    }


    /**
     * 创建api
     *
     * @param baseUrl
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createApi(String baseUrl,final Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(service);
    }






}
