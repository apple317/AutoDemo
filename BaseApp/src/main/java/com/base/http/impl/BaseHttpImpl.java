package com.base.http.impl;

import com.base.http.common.HttpConfiguration;


/**
 * 请求对象模板接口
 * BaseHttpClient such is the definition of a common network parameters into the model,
 * all network expansion interface to implement the first
 * interface to define the request, and can be customized to achieve a new interface
 @author applehsp
 */


public interface BaseHttpImpl{

	/**
	 * 配置初始化操作
	 */
	void init(HttpConfiguration configuration);

}
