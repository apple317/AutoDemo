package com.base.http.retrofit;

import com.base.http.common.BaseHttpClient;
import com.base.http.entity.METHOD;
import com.base.http.params.BaseParams;
import com.base.http.rxjava.BaseObservable;
import com.base.http.rxjava.RxSubscriber;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nullable;

import rx.plugins.RxJavaHooks;


/**
 * Created by apple_hsp on 17/7/17.
 */

public final class Retrofit {
    //缓存
    private final Map<String, BaseHttpClient> serviceMethodCache = new LinkedHashMap<>();

    public <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, @Nullable Object[] args)
                            throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }
                        //获取返回值泛型信息
                        Type returnType = method.getGenericReturnType();
                        Type[] typeArguments = new Type[0];
                        if (returnType instanceof ParameterizedType) {
                            typeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
                        }
                        BaseParams baseParms =loadServiceArgs(args);
                        BaseHttpClient baseHttpClient = loadServiceMethod(method, baseParms);
                        baseHttpClient.setType(typeArguments[0]);
                        return new BaseObservable<T>(RxJavaHooks.onCreate(new RxSubscriber<T>(baseHttpClient){}));
                    }
                });
    }


    public BaseParams loadServiceArgs(Object[] args) {
        BaseParams baseParams=new BaseParams();
        for(Object object:args){
            if(object instanceof PartMap){
                Iterator iter = ((Map)object).entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    if(entry.getValue() instanceof File){
                        try {
                            baseParams.put(key,(File)entry.getValue());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }else{
                        baseParams.put(key,(String)entry.getValue());
                    }
                }
            }else if(object instanceof Part){
               Object partVal = ((Part) object).value();
               if(partVal instanceof File){
                   //baseParams.put(,(File)partVal);
               }else{

               }
            }

        }
        return baseParams;
    }



    /**
     * @param method
     */
    public BaseHttpClient loadServiceMethod(Method method, BaseParams baseParms) {
        BaseHttpClient baseHttpClient = BaseHttpClient.getBaseClient();
        String methodName = "";
        String url = "";
        Class parse = null;
        synchronized (serviceMethodCache) {
            String httpTag = "";
            for (Annotation annotation : method.getAnnotations()) {
//                if (annotation instanceof GETFILE) {
//                    httpTag = ((GETFILE) annotation).value();
//                    methodName = METHOD.GET_FILE;
//                    url = baseHttpClient.configuration.getBaseUrl() + httpTag;
//                } else
                if (annotation instanceof GET) {
                    httpTag = ((GET) annotation).value();
                    methodName = METHOD.GET;
                    url = baseHttpClient.configuration.getBaseUrl() + httpTag;
                } else if (annotation instanceof POST) {
                    httpTag = ((POST) annotation).value();
                    methodName = METHOD.POST_FORM;
                    url = baseHttpClient.configuration.getBaseUrl() + httpTag;
                } else if (annotation instanceof PUT) {
                    httpTag = ((PUT) annotation).value();
                    methodName = METHOD.PUT;
                    url = baseHttpClient.configuration.getBaseUrl() + httpTag;
                }
//                else if (annotation instanceof DOWN) {
//                    httpTag = ((DOWN) annotation).value();
//                    methodName = METHOD.DOWNLOAD_FILE;
//                    url = baseHttpClient.configuration.getBaseUrl() + httpTag;
//                } else if (annotation instanceof PostJson) {
//                    httpTag = ((PostJson) annotation).value();
//                    methodName = METHOD.POST_STRING;
//                    url = baseHttpClient.configuration.getBaseUrl() + httpTag;
//                } else if (annotation instanceof PostFormFile) {
//                    httpTag = ((PostFormFile) annotation).value();
//                    methodName = METHOD.POST_FORM_FILE;
//                    url = baseHttpClient.configuration.getBaseUrl() + httpTag;
//                }
            }
            baseHttpClient = new BaseHttpClient.Builder()
                    .url(url).method(methodName)
                    .setParse(parse)
                    .setTag(httpTag)
                    .setBaseParams(baseParms)
                    .build();
            serviceMethodCache.put(method.getName(), baseHttpClient);
        }
        return baseHttpClient;
    }


}
