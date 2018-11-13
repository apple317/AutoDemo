package com.base.http.rxjava;

import android.text.TextUtils;
import android.util.Log;

import com.base.http.common.BaseHttpClient;
import com.base.http.gsonfactory.CustomGsonConverterFactory;
import com.base.http.listener.error.HttpTimeException;
import com.base.http.listener.okhttp.download.DownInfo;
import com.base.http.listener.okhttp.download.DownState;
import com.base.http.listener.okhttp.download.DownloadResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * des:订阅封装
 * Created by xsf
 * on 2016.09.10:16
 */

/********************使用例子********************/
public abstract class RxSubscriber<T> implements Observable.OnSubscribe<T> {
    BaseHttpClient baseHttpClient;

    public RxSubscriber(BaseHttpClient httpClient) {
        this.baseHttpClient=httpClient;
    }


    @Override
    public void call(Subscriber<? super T> subscriber) {
        Log.e("HU","======RxSubscriber====call=");
        baseHttpClient.execute(baseHttpClient, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                subscriber.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
//                    if(baseHttpClient.getType() instanceof DownloadResponseBody){
//                        String fileName=getHeaderFileName(response);
//                        if (TextUtils.isEmpty(fileName)) {
//                            fileName=MD5Util.encrypt(baseHttpClient.getUrl());
//                        }
//                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),fileName);
//                        writeCaches(new DownloadResponseBody(response.body()),file,new DownInfo(baseHttpClient.getUrl(),file.getPath()));
//                     //   subscriber.
//                    }else if(baseHttpClient.getType() instanceof ProgressRequestBody){
//
//                    }else{
//                        T t = (T) CustomGsonConverterFactory.create().responseBodyConverter(baseHttpClient.getType()).convert(response.body());
//                        subscriber.onNext(t);
//                    }
                    T t = (T) CustomGsonConverterFactory.create().responseBodyConverter(baseHttpClient.getType()).convert(response.body());
                    subscriber.onNext(t);
                }else{
                    subscriber.onError(new Throwable(response.message()));
                }
            }
        });
    }


    /**
     * 写入文件
     *
     * @param info
     * @throws IOException
     */
    public void writeCaches(DownloadResponseBody responseBody,File file,DownInfo info) {
        try {
            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                long allLength = 0 == info.getCountLength() ? responseBody.contentLength() : info.getReadLength() + responseBody
                        .contentLength();

                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                        info.getReadLength(), allLength - info.getReadLength());
                byte[] buffer = new byte[1024 * 4];
                int len;
                info.setCountLength(allLength);
                while ((len = inputStream.read(buffer)) != -1) {
                    mappedBuffer.put(buffer, 0, len);
                    info.setReadLength(info.getReadLength()+len);
                }
                info.setState(DownState.DOWN);
            } catch (IOException e) {
                info.setState(DownState.ERROR);
                throw new HttpTimeException(e.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
        } catch (IOException e) {
            info.setState(DownState.ERROR);
            throw new HttpTimeException(e.getMessage());
        }
    }


    /**
     * 解析文件头
     * Content-Disposition:attachment;filename=FileName.txt
     * Content-Disposition: attachment; filename*="UTF-8''%E6%9B%BF%E6%8D%A2%E5%AE%9E%E9%AA%8C%E6%8A%A5%E5%91%8A.pdf"
     */
    private static String getHeaderFileName(Response response) {
        String dispositionHeader = response.header("Content-Disposition");
        if (!TextUtils.isEmpty(dispositionHeader)) {
            dispositionHeader.replace("attachment;filename=", "");
            dispositionHeader.replace("filename*=utf-8", "");
            String[] strings = dispositionHeader.split("; ");
            if (strings.length > 1) {
                dispositionHeader = strings[1].replace("filename=", "");
                dispositionHeader = dispositionHeader.replace("\"", "");
                return dispositionHeader;
            }
            return "";
        }
        return "";
    }



}
