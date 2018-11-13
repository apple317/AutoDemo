package com.base.mvp.annotations;


import com.base.mvp.common.BasePresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * yangyoupeng  on 2018/4/25.
 * <p>
 * 一个创建presenter的注解
 * @Inherited  这个注解表示  只能在类上使用并且是运行时
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePresenterAnnotation {

    Class<? extends BasePresenter> value();
}
