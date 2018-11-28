package com.zyh.wanandroid;

import android.app.Application;
import android.content.Context;

import com.common.app.AppComponent;
import com.common.app.AppModule;
import com.common.app.DaggerAppComponent;
import com.common.util.CrashHandler;
import com.common.util.NetworkUtils;
import com.common.util.PrefsUtils;
import com.zyh.wanandroid.di.component.ActivityComponent;
import com.zyh.wanandroid.di.component.ApiComponent;
import com.zyh.wanandroid.di.component.DaggerActivityComponent;
import com.zyh.wanandroid.di.component.DaggerApiComponent;
import com.zyh.wanandroid.di.component.DaggerFragmentComponent;
import com.zyh.wanandroid.di.component.FragmentComponent;
import com.zyh.wanandroid.di.module.ActivityModule;
import com.zyh.wanandroid.di.module.ApiModule;
import com.zyh.wanandroid.di.module.FragmentModule;

/**
 * author : zyh
 * Date : 2018/11/26
 * Description :
 */
public class MyApp extends Application {

    private static MyApp app = null;
    private AppComponent build;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;



        initPrefs();
        initNetwork();
        initCrashHandler();
        if (build == null)
        build = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }


    public static synchronized MyApp getInstance(){
        return app;
    }

    public ApiComponent getApiComponent(){
        return DaggerApiComponent.builder().appComponent(build).apiModule(new ApiModule()).build();
    }

    public ActivityComponent getActivityComponent(){
        return DaggerActivityComponent.builder().apiComponent(getApiComponent()).activityModule(new ActivityModule()).build();
    }

    public FragmentComponent getFragmentComponent(){
        return DaggerFragmentComponent.builder().apiComponent(getApiComponent()).fragmentModule(new FragmentModule()).build();
    }
    /**
     * 初始化网络监听
     */
    private void initNetwork() {
        NetworkUtils.startNetService(getApplicationContext());
    }
    /**
     * 初始化崩溃日志
     */
    private void initCrashHandler() {
        CrashHandler.getInstance().init(getApplicationContext());
    }
    /**
     * 初始化sp
     */
    private void initPrefs() {
        PrefsUtils.init(this, getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }
}

