package com.zyh.wanandroid.di.component;

import com.zyh.wanandroid.di.module.ActivityModule;
import com.zyh.wanandroid.di.scope.PerActivity;
import com.zyh.wanandroid.ui.splash.SplashActivity;

import dagger.Component;

/**
 * author : zyh
 * Date : 2018/11/28
 * Description :
 */
@PerActivity
@Component(dependencies = ApiComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);
}
