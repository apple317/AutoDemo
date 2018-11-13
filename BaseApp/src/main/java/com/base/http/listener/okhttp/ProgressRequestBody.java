//package com.base.http.listener.okhttp;///**
//// * Copyright 2015 ZhangQu Li
//// * <p/>
//// * Licensed under the Apache License, Version 2.0 (the "License");
//// * you may not use this file except in compliance with the License.
//// * You may obtain a copy of the License at
//// * <p/>
//// * http://www.apache.org/licenses/LICENSE-2.0
//// * <p/>
//// * Unless required by applicable law or agreed to in writing, software
//// * distributed under the License is distributed on an "AS IS" BASIS,
//// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//// * See the License for the specific language governing permissions and
//// * limitations under the License.
//// */
//package com.lottery.com.base.http.listener.okhttp;
//
//
//
//import com.lottery.com.base.http.common.BaseHttpClient;
//import com.lottery.com.base.http.listener.HttpResult;
//import com.lottery.com.base.http.retrofit.BaseSubscriber;
//
//import java.io.IOException;
//
//import okhttp3.MediaType;
//import okhttp3.RequestBody;
//import okio.Buffer;
//import okio.BufferedSink;
//import okio.ForwardingSink;
//import okio.Okio;
//import okio.Sink;
//
//
//public  class ProgressRequestBody<T> extends RequestBody {
//    //实际的待包装请求体
//    private final RequestBody requestBody;
//
//    //包装完成的BufferedSink
//    private BufferedSink bufferedSink;
//    BaseHttpClient mClient;
//    BaseSubscriber<T> observer;
//    /**
//     * 构造函数，赋值
//     * @param requestBody 待包装的请求体
//     */
//    public ProgressRequestBody(BaseHttpClient client, BaseSubscriber<T> observer, RequestBody requestBody) {
//        this.requestBody = requestBody;
//        mClient = client;
//        this.observer=observer;
//    }
//
//    /**
//     * 重写调用实际的响应体的contentType
//     * @return MediaType
//     */
//    @Override
//    public MediaType contentType() {
//        return requestBody.contentType();
//    }
//
//    /**
//     * 重写调用实际的响应体的contentLength
//     * @return contentLength
//     * @throws IOException 异常
//     */
//    @Override
//    public long contentLength() throws IOException {
//        return requestBody.contentLength();
//    }
//
//    /**
//     * 重写进行写入
//     * @param sink BufferedSink
//     * @throws IOException 异常
//     */
//    @Override
//    public void writeTo(BufferedSink sink) throws IOException {
//        if (bufferedSink == null) {
//            //包装
//            bufferedSink = Okio.buffer(sink(sink));
//        }
//        //写入
//        requestBody.writeTo(bufferedSink);
//        //必须调用flush，否则最后一部分数据可能不会被写入
//        bufferedSink.flush();
//
//
//    }
//
//
//
//    /**
//     * 写入，回调进度接口
//     * @param sink Sink
//     * @return Sink
//     */
//    private Sink sink(Sink sink) {
//        return new ForwardingSink(sink) {
//            //当前写入字节数
//            long bytesWritten = 0L;
//            //总字节长度，避免多次调用contentLength()方法
//            long contentLength = 0L;
//            @Override
//            public void write(Buffer source, long byteCount) throws IOException {
//                super.write(source, byteCount);
//                if (contentLength == 0) {
//                    //获得contentLength的值，后续不再调用
//                    contentLength = contentLength();
//                }
//                //增加当前写入的字节数
//                bytesWritten += byteCount;
//                //回调
//                HttpResult<T> httpResult = new HttpResult();
//                httpResult.bytesRead = bytesWritten;
//                httpResult.contentLength = contentLength;
//                httpResult.httCode=200;
//                httpResult.tag=mClient.getTag().toString();
//                observer.onNext((T) httpResult);
//            }
//        };
//    }
//}
