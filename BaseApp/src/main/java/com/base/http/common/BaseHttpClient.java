package com.base.http.common;


import com.base.http.impl.okhttp.OkHttpImpl;
import com.base.http.rxjava.BaseObservable;


/**
 * BaseHttpClient such is the definition of a common network parameters into the model,
 * all network expansion interface to implement the first interface to define the request,
 * and can be customized to achieve a new interface
 *
 * @author applehsp
 */
public class BaseHttpClient {

    public static final String TAG = BaseHttpClient.class.getSimpleName();
    //单例模式实现
    static BaseHttpClient baseClient = null;


    public static HttpConfiguration configuration;
    private static final String WARNING_RE_INIT_CONFIG = "Try to initialize BaseHttpClient which had already been initialized before. " + "To re-init BaseHttpClient with new configuration call BaseHttpClient.destroy() at first.";
    private static final String ERROR_INIT_CONFIG_WITH_NULL = "BaseHttpClient configuration can not be initialized with null";
    static final String LOG_INIT_CONFIG = "Initialize BaseHttpClient with configuration";

    private BaseObservable baseObservable;


    public static BaseHttpClient getBaseClient() {
        if (baseClient == null) {
            synchronized (BaseHttpClient.class) {
                if (baseClient == null) {
                    baseClient = new BaseHttpClient();
                }
            }
        }
        return baseClient;
    }

    public BaseObservable getBaseObservable() {
        return baseObservable;
    }

    public BaseHttpClient() {
    }

    private BaseHttpClient(Builder builder) {

    }


    public void setBaseObservable(BaseObservable baseObservable) {
        this.baseObservable = baseObservable;
    }


    public static HttpConfiguration getConfiguration() {
        return configuration;
    }


    /**
     * Initializes ImageLoader instance with configuration.<br />
     * If configurations was set before ( {@link == true) then this method does nothing.<br />
     *
     * @param httpConfiguration {@linkplain HttpConfiguration BaseHttpClient configuration}
     * @throws IllegalArgumentException if <b>configuration</b> parameter is null
     */
    public synchronized void init(HttpConfiguration httpConfiguration) {
        if (httpConfiguration == null) {
            throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
        }
        this.configuration = httpConfiguration;
        getHttpImpl().init(configuration);
    }


    public static Builder newBuilder() {
        return new Builder();
    }


    /**
     * 网络申请创建一个api
     *
     * @return
     */
    public <T> T createApi(final Class<T> service) {
        return OkHttpImpl.getOkClient().createApi(service);
    }


    /**
     * 创建新的地址
     *
     * @return T api
     */
    public <T> T createApi(String baseUrl, final Class<T> service) {
        return OkHttpImpl.getOkClient().createApi(service);
    }


    /**
     * 得到当前网络扩展类
     * 得到okhttp扩展类
     * 切换网络库记住此处切换
     *
     * @return
     */
    public OkHttpImpl getHttpImpl() {
        return OkHttpImpl.getOkClient();
    }


    public static final class Builder {

        public Builder() {

        }


        public BaseHttpClient build() {
            return new BaseHttpClient(this);
        }

    }


}
