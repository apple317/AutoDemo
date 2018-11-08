package com.base.di.module;//package com.lottery.base.di.module;
//
//import android.app.Application;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.lottery.app.di.net.api.ApiService;
//import com.lottery.base.util.SharePrefUtils;
//import com.lottery.base.di.qualifier.AppContext;
//import com.lottery.base.di.qualifier.PreferencesName;
//import com.lottery.base.util.LogUtil;
//
//import java.util.concurrent.TimeUnit;
//
//import javax.inject.Singleton;
//
//import dagger.Module;
//import dagger.Provides;
//import okhttp3.Cache;
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by SeanLiu on 17/6/16.
// * ApiServiceModule
// */
//
//@Module
//public class ApiServiceModule {
//
//    @Provides
//    @Singleton
//    SharePrefUtils provideSharePrefUtils(@AppContext Application application, @PreferencesName String name) {
//        return new SharePrefUtils(application, name);
//    }
//
//    @Singleton
//    @Provides
//    Gson provideGson() {
//        GsonBuilder builder = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        return builder.create();
//    }
//
//    @Singleton
//    @Provides
//    Cache provideOkHttpCache(Application application) {
//        int cacheSize = 10 * 1024 * 1024;
//        Cache cache = new Cache(application.getCacheDir(), cacheSize);
//        return cache;
//    }
//
//    @Singleton
//    @Provides
//    OkHttpClient provideOkHttpClient(Cache cache) {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        if (LogUtil.isOpen) {
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        } else {
//            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
//        }
//        OkHttpClient okHttpClient;
//        try {
//            okHttpClient = new OkHttpClient.Builder()
//                    .addInterceptor(interceptor)
//                    .cache(cache)
//                    .retryOnConnectionFailure(true)
//                    .connectTimeout(15, TimeUnit.SECONDS)
//                    .build();
//        } catch (Exception e) {
//            return null;
//        }
//        return okHttpClient;
//    }
//
//    @Singleton
//    @Provides
//    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("")
//                .client(okHttpClient)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        return retrofit;
//    }
//
//    @Singleton
//    @Provides
//    ApiService provideApiService(Retrofit retrofit) {
//        return retrofit.create(ApiService.class);
//    }
//}
