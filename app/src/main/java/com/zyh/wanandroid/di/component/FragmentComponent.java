package com.zyh.wanandroid.di.component;

import com.zyh.wanandroid.di.module.FragmentModule;
import com.zyh.wanandroid.di.scope.PerFragment;

import dagger.Component;

/**
 * author : zyh
 * Date : 2018/11/28
 * Description :
 */
@PerFragment
@Component(dependencies = ApiComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {
}
