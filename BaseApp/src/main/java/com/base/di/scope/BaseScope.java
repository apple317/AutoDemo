package com.base.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by SeanLiu on 17/6/16.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseScope {
}
