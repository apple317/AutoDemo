package com.base.http.listener.okhttp;//package com.lottery.com.base.http.listener.okhttp;
//
//
//import android.util.Log;
//
//import com.lottery.com.base.http.common.BaseHttpClient;
//import com.google.gson.Gson;
//import com.lottery.com.base.http.listener.HttpResult;
//import com.lottery.com.base.http.retrofit.BaseSubscriber;
//import com.lottery.com.base.util.LogUtil;
//
//
//import java.io.IOException;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
//
///**
// * Such through the realization of the callback interface
// * to achieve data back to the network operation,
// * such main achieve the return data analysis, refresh the UI, notify the organizer.
// * You can access the source by the following address
// *
// * @author applehsp
// */
//public class BaseOkCall<T> implements Callback {
//
//    //httpcallback是自定义的请求返回对象
//
//    //url是请求地址
//    BaseHttpClient mClient;
//    BaseSubscriber<T> observer;
//    public BaseOkCall(BaseHttpClient client,BaseSubscriber<T> observer) {
//        mClient = client;
//        this.observer=observer;
//    }
//
//
//    /**
//     * Called when the request could not be executed due to cancellation, a
//     * connectivity problem or timeout. Because networks can fail during an
//     * exchange, it is possible that the remote server accepted the request
//     * before the failure.
//     */
//    @Override
//    public void onFailure(Call call, IOException e) {
//        LogUtil.e("Lottery","Failure tag: "+mClient.getTag());
//        BaseHttpClient.configuration.getHandler().post(new Runnable() {
//            @Override
//            public void run() {
//                observer.onError(e);
//            }
//        });
//    }
//
//    /**
//     * Called when the HTTP response was successfully returned by the remote
//     * server. The callback may proceed to read the response body with {@link
//     * }. The response is still live until its response body is
//     * closed with {@code response.body().close()}. The recipient of the callback
//     * may even consume the response body on another thread.
//     * <p/>
//     * <p>Note that transport-layer success (receiving a HTTP response code,
//     * headers and body) does not necessarily indicate application-layer
//     * success: {@code response} may still indicate an unhappy HTTP response
//     * code like 404 or 500.
//     */
//    @Override
//    public void onResponse(Call call, Response response) throws IOException {
//        try {
//            LogUtil.e("Lottery", "Content: " + response.code());
//            HttpResult<T> httpResult = new HttpResult<>();
//            httpResult.httCode = response.code();
//            httpResult.tag=mClient.getTag().toString();
//            String content="";
//            if (response.isSuccessful()) {
//                //成功得到文本信息
//                content=response.body().string();
//                LogUtil.e("Lottery", "Content: " + content);
//                if (mClient != null && mClient.getParse() != null) {
//                    httpResult.parse= (T) new Gson().fromJson(content,mClient.getParse());
//                }
//                httpResult.httpResultContent =content;
//                if(response!=null&&response.headers()!=null&&response.header("token")!=null)
//                    httpResult.token=response.header("token");
//            }
//            BaseHttpClient.configuration.getHandler().post(new Runnable() {
//                @Override
//                public void run() {
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            // TODO Auto-generated catch block
//            BaseHttpClient.configuration.getHandler().post(new Runnable() {
//                @Override
//                public void run() {
//                    observer.onError(e);
//                }
//            });
//        }
//    }
//
//
//}
