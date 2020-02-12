package com.zyh.wanandroid.di.component;

import com.common.app.AppComponent;
import com.zyh.wanandroid.di.module.ApiModule;
import com.zyh.wanandroid.di.scope.GlobalApis;
import com.zyh.wanandroid.net.AppApis;

import dagger.Component;

/**
 * author : zyh
 * Date : 2019/1/23
 * Description :
 */
@GlobalApis
@Component(dependencies = AppComponent.class,modules = ApiModule.class)
public interface ApiComponent {
    AppApis getAppApis();
}
