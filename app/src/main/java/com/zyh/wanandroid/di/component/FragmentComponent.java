package com.zyh.wanandroid.di.component;

import com.zyh.wanandroid.di.module.FragmentModule;
import com.zyh.wanandroid.di.scope.PerFragment;
import com.zyh.wanandroid.ui.article.ArticleFragment;
import com.zyh.wanandroid.ui.category.CategoryFragment;
import com.zyh.wanandroid.ui.home.HomeFragment;
import com.zyh.wanandroid.ui.mine.MineFragment;

import dagger.Component;

/**
 * author : zyh
 * Date : 2018/11/28
 * Description :
 */
@PerFragment
@Component(dependencies = ApiComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(HomeFragment homeFragment);
    void inject(ArticleFragment articleFragment);
    void inject(CategoryFragment categoryFragment);
    void inject(MineFragment mineFragment);
}
