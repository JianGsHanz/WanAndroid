package com.zyh.wanandroid.di.component

import com.zyh.wanandroid.di.module.ActivityModule
import com.zyh.wanandroid.di.scope.PerActivity
import dagger.Component

/**
 * author : zyh
 * Date : 2018/11/23
 * Description :
 */
@PerActivity
@Component(dependencies = arrayOf(ApiComponent ::class),modules = arrayOf(ActivityModule::class))
public interface ActivityComponent{

}