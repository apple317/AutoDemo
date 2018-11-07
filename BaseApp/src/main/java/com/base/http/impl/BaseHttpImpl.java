package com.base.http.impl;

import com.base.http.common.BaseHttpClient;
import com.base.http.common.HttpConfiguration;

import okhttp3.Callback;


/**
 * 请求对象模板接口
 * BaseHttpClient such is the definition of a common network parameters into the model,
 * all network expansion interface to implement the first
 * interface to define the request, and can be customized to achieve a new interface
 @author applehsp
 */


public interface BaseHttpImpl<T>{
	/**
	 * 网络库接口定义
	 *   public static final String GET = "GET";
	 public static final String POST_STRING = "POST_STRING";
	 public static final String POST_FORM = "POST_FORM";
	 public static final String POST_FILE = "POST_FILE";
	 public static final String DOWNLOAD_FILE = "DOWN_FILE";
	 public static final String POST_FILE_PROGRESS = "POST_FILE_PROGRESS";
	 public static final String POST_FORM_PROGRESS = "POST_FORM_PROGRESS";
	 public static final String PUT = "PUT";
	 public static final String PATCH = "PATCH";
	 public static final String DELETE = "DELETE";
	 */


	void execute(BaseHttpClient client, Callback callback);
	/**
	 * 配置初始化操作
	 */
	void init(HttpConfiguration configuration);

	void stopExecute();
}
