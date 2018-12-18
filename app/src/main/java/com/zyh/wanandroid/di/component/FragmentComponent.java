package com.zyh.wanandroid.di.component;

import com.zyh.wanandroid.di.module.FragmentModule;
import com.zyh.wanandroid.di.scope.PerFragment;
import com.zyh.wanandroid.ui.knowledge.article.KnowledgeArticleFragment;
import com.zyh.wanandroid.ui.knowledge.article.KnowledgeListFragment;
import com.zyh.wanandroid.ui.knowledge.knowledgeFragment;
import com.zyh.wanandroid.ui.category.CategoryFragment;
import com.zyh.wanandroid.ui.home.HomeFragment;
import com.zyh.wanandroid.ui.login.LoginRegisterFragment;
import com.zyh.wanandroid.ui.main.MainFragment;
import com.zyh.wanandroid.ui.mine.MineFragment;
import com.zyh.wanandroid.ui.navigation.NavigationFragment;
import com.zyh.wanandroid.ui.web.WebFragment;

import dagger.Component;

/**
 * author : zyh
 * Date : 2018/11/28
 * Description :
 */
@PerFragment
@Component(dependencies = ApiComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(MainFragment mainFragment);
    void inject(HomeFragment homeFragment);
    void inject(NavigationFragment navigationFragment);
    void inject(knowledgeFragment knowledgeFragment);
    void inject(CategoryFragment categoryFragment);
    void inject(MineFragment mineFragment);
    void inject(WebFragment webFragment);
    void inject(LoginRegisterFragment loginFragment);
    void inject(KnowledgeArticleFragment knowledgeArticleFragment);
    void inject(KnowledgeListFragment knowledgeListFragment);
}
