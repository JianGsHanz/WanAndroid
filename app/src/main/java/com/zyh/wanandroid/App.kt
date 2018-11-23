package com.zyh.wanandroid

import android.app.Application
import com.common.app.AppComponent
import com.common.app.AppModule
import com.common.app.DaggerAppComponent
import com.zyh.wanandroid.di.component.ActivityComponent
import com.zyh.wanandroid.di.component.ApiComponent
import com.zyh.wanandroid.di.component.DaggerApiComponent
import com.zyh.wanandroid.di.module.ActivityModule
import com.zyh.wanandroid.di.module.ApiModule

/**
 * author : zyh
 * Date : 2018/11/23
 * Description :
 */
class App : Application() {

    var build : AppComponent? = null
    companion object {
        var app : App ?=null
         fun getInstance() = app

    }
    override fun onCreate() {
        super.onCreate()
        app = this

        build = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    val apiComponent : ApiComponent
     get() = DaggerApiComponent.builder().appComponent(build).apiModule(ApiModule()).build()

    val activityComponent :ActivityComponent
    get() = DaggerActivityComponent.builder().apiComponent(apiComponent).activityModule(ActivityModule()).build()
}
