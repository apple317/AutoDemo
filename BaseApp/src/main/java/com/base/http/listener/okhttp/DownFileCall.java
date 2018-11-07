package com.base.http.listener.okhttp;//package com.lottery.com.base.http.listener.okhttp;
//
//import android.content.Context;
//
//
//import com.lottery.com.base.http.entity.DownEntity;
//import com.lottery.com.base.http.entity.DownType;
//import com.lottery.com.base.http.listener.HttpResult;
//import com.lottery.com.base.http.retrofit.BaseSubscriber;
//import com.lottery.com.base.http.params.StorageUtils;
//import com.lottery.com.base.util.MachineInfo;
//import com.lottery.com.base.http.common.BaseHttpClient;
//import com.lottery.com.base.util.MD5Util;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//
//
///**
// * @author applehsp
// *
// */
//public class DownFileCall<T>  implements Callback {
//
//
//    //url是请求地址
//    String url;
//    /**
//     * 目标文件存储的文件夹路径
//     */
//    private String destFileDir;
//    /**
//     * 目标文件存储的文件名
//     */
//    private String destFileName;
//    Context mContext;
//
//    DownEntity mDownEntity;
//
//    BaseHttpClient baseHttpClient;
//    BaseSubscriber<T> observer;
//
//    public DownFileCall(BaseHttpClient baseHttpClient, BaseSubscriber<T> baseObserver, String requestUrl, String destFileDir, String destFileName) {
//        this.url = requestUrl;
//        this.destFileDir = destFileDir;
//        this.destFileName = destFileName;
//        this.observer=baseObserver;
//        this.mContext = baseHttpClient.getConfiguration().getContext();
//        this.baseHttpClient = baseHttpClient;
//        mDownEntity = new DownEntity();
//        mDownEntity.downUrl(url);
//        if (destFileDir != null && destFileDir.trim().toString().equals(""))
//            mDownEntity.downDir(destFileDir);
//        if (destFileName != null && destFileName.trim().toString().equals(""))
//            mDownEntity.downName(destFileName);
//    }
//
//
//    public Context getContext() {
//        return mContext;
//    }
//
//    /**
//     * Called when the request could not be executed due to cancellation, a
//     * connectivity problem or timeout. Because networks can fail during an
//     * exchange, it is possible that the remote server accepted the request
//     * before the failure.
//     */
//    @Override
//    public void onFailure(Call call, IOException e) {
//        observer.onError(e);
//    }
//
//    /**
//     * Called when the HTTP response was successfully returned by the remote
//     * server. The callback may proceed to read the response body with {@link
//     * Response#body}. The response is still live until its response body is
//     * closed with {@code response.body().close()}. The recipient of the callback
//     * may even consume the response body on another thread.
//     * <p>
//     * <p>Note that transport-layer success (receiving a HTTP response code,
//     * headers and body) does not necessarily indicate application-layer
//     * success: {@code response} may still indicate an unhappy HTTP response
//     * code like 404 or 500.
//     */
//    @Override
//    public void onResponse(Call call, Response response) throws IOException {
//        try {
//            HttpResult<T> httpResult = new HttpResult<>();
//            httpResult.httCode = response.code();
//            httpResult.tag=baseHttpClient.getTag().toString();
//            if (response.isSuccessful()) {
//                //成功得到文本信息
//                mDownEntity.downCode(response.code());
//                mDownEntity.downMessage(response.body().toString());
//                saveFile(response);
//            } else {
//                mDownEntity.downStatue(DownType.DOWNLOAD_ERROR);
//                mDownEntity.downCode(response.code());
//                mDownEntity.downMessage(response.body().toString());
//                httpResult.parse= (T) mDownEntity;
//            }
//            observer.onNext((T) httpResult);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            observer.onError(e);
//        }
//    }
//
//    /**
//     * 文件保存下载
//     */
//    public File saveFile(Response response) throws IOException {
//        InputStream is = null;
//        byte[] buf = new byte[2048];
//        int len = 0;
//        FileOutputStream fos = null;
//        try {
//            is = response.body().byteStream();
//            final long total = response.body().contentLength();
//            long sum = 0;
//            File dir;
//            long sdcardAvailable = MachineInfo
//                    .getAvailableExternalMemorySize();
//            if (sdcardAvailable < total) {
//                mDownEntity.downStatue(DownType.DOWNLOAD_SPACE);
//                HttpResult<T> httpResult = new HttpResult<>();
//                httpResult.httCode = response.code();
//                httpResult.tag=baseHttpClient.getTag().toString();
//                httpResult.parse= (T) mDownEntity;
//                observer.onNext((T) httpResult);
//                return null;
//            }
//
//            if (destFileDir == null || destFileDir.trim().toString().equals("")) {
//                destFileDir = StorageUtils.getCacheDirectory(mContext).getAbsolutePath();
//                dir = new File(destFileDir);
//            } else {
//                dir = new File(destFileDir);
//            }
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            if (destFileName == null || destFileName.trim().toString().equals("")) {
//                destFileName = MD5Util.encrypt(url) + "." + resolve(url);
//            }
//            File file = new File(dir, destFileName);
//            mDownEntity.downPath(file.getPath());
//            mDownEntity.downName(destFileName);
//            fos = new FileOutputStream(file);
//            long time = System.currentTimeMillis();
//
//            while ((len = is.read(buf)) != -1) {
//                sum += len;
//                fos.write(buf, 0, len);
//                final long finalSum = sum;
//                mDownEntity.currentByte(finalSum);
//                mDownEntity.totalByte(total);
//                mDownEntity.downStatue(DownType.DOWNLOAD_GOING);
//                long current = System.currentTimeMillis();
//                if ((current - time) > 400) {
//                    time = current;
//                    HttpResult<T> httpResult = new HttpResult<>();
//                    httpResult.httCode = response.code();
//                    httpResult.tag=baseHttpClient.getTag().toString();
//                    httpResult.parse= (T) mDownEntity;
//                }
//            }
//            fos.flush();
//            mDownEntity.downStatue(DownType.DOWNLOAD_COMPLETE);
//            HttpResult<T> httpResult = new HttpResult<>();
//            httpResult.httCode = response.code();
//            httpResult.tag=baseHttpClient.getTag().toString();
//            httpResult.parse= (T) mDownEntity;
//            return file;
//
//        } finally {
//            try {
//                if (is != null) is.close();
//            } catch (IOException e) {
//                observer.onError(e);
//            }
//            try {
//                if (fos != null) fos.close();
//            } catch (IOException e) {
//                observer.onError(e);
//            }
//
//        }
//    }
//
//    public static String resolve(String downHttpUrl) {
//        // TODO Auto-generated method stub
//        String httpUrl = "mp4";
//        String[] downArray = downHttpUrl.split(".");
//        int length = downArray.length;
//        if (length > 0) {
//            httpUrl = downArray[length - 1];
//        }
//        return httpUrl;
//    }
//
//}
