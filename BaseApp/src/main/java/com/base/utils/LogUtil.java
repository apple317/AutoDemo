/*
 * Copyright (C) 2012 www.amsoft.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.base.utils;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;

// TODO: Auto-generated Javadoc

/**
 * XJ
 */
public class LogUtil {

    /**
     * debug开关.
     */
    public static boolean isOpen =  false;

    public static String INTERCEPTOR_TAG_STR = "lottery";

    /**
     * 起始执行时间.
     */
    public static long startLogTimeInMillis = 0;

    /**
     * debug日志
     *
     * @param tag
     * @param message
     */
    public static void d(String tag, String message) {
        if (isOpen) Log.d(tag, message);
    }

    /**
     * debug日志
     *
     * @param message
     */
    public static void d(String message) {
        if (isOpen)
            Log.d(INTERCEPTOR_TAG_STR, message);
    }

    /**
     * info日志
     *
     * @param message
     */
    public static void i(String message) {
        if (isOpen)
            Log.i(INTERCEPTOR_TAG_STR, message);
    }

    /**
     * info日志
     *
     * @param tag
     * @param message
     */
    public static void i(String tag, String message) {
        if (isOpen)
            Log.i(tag, message);
    }


    /**
     * error日志
     *
     * @param tag
     * @param message
     */
    public static void e(String tag, String message) {
        if (isOpen)
            Log.e(tag, message);
    }

    /**
     * error日志
     *
     * @param message
     */
    public static void e( String message) {
        if (isOpen)
            Log.e(INTERCEPTOR_TAG_STR, message);
    }



    /**
     * 描述：记录当前时间毫秒.
     */
    public static void prepareLog(String tag) {
        Calendar current = Calendar.getInstance();
        startLogTimeInMillis = current.getTimeInMillis();
        Log.d(tag, "日志计时开始：" + startLogTimeInMillis);
    }

    /**
     * 描述：记录当前时间毫秒.
     */
    public static void prepareLog(Context context) {
        String tag = context.getClass().getSimpleName();
        prepareLog(tag);
    }

    /**
     * 描述：记录当前时间毫秒.
     */
    public static void prepareLog(Class<?> clazz) {
        String tag = clazz.getSimpleName();
        prepareLog(tag);
    }

    /**
     * 描述：打印这次的执行时间毫秒，需要首先调用prepareLog().
     *
     * @param tag       标记
     * @param message   描述
     * @param printTime 是否打印时间
     */
    public static void d(String tag, String message, boolean printTime) {
        Calendar current = Calendar.getInstance();
        long endLogTimeInMillis = current.getTimeInMillis();
        Log.d(tag, message + ":" + (endLogTimeInMillis - startLogTimeInMillis) + "ms");
    }


    /**
     * 描述：打印这次的执行时间毫秒，需要首先调用prepareLog().
     *
     * @param message   描述
     * @param printTime 是否打印时间
     */
    public static void d(Context context, String message, boolean printTime) {
        String tag = context.getClass().getSimpleName();
        d(tag, message, printTime);
    }

    /**
     * 描述：打印这次的执行时间毫秒，需要首先调用prepareLog().
     *
     * @param clazz     标记
     * @param message   描述
     * @param printTime 是否打印时间
     */
    public static void d(Class<?> clazz, String message, boolean printTime) {
        String tag = clazz.getSimpleName();
        d(tag, message, printTime);
    }


}
