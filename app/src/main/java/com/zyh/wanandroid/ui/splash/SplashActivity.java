package com.zyh.wanandroid.ui.splash;

import com.common.base.BaseActivity;
import com.common.base.IBaseMvpActivity;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;

import javax.inject.Inject;

/**
 * author : zyh
 * Date : 2018/11/29
 * Description :
 */
public class SplashActivity extends BaseActivity implements IBaseMvpActivity<SplashPresenter>,SplashContract.view {

    @Inject
    SplashPresenter presenter;

    @Override
    public void initViewAndEvent() {

    }

    @Override
    public SplashPresenter getPresenter() {
        return presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initInject() {
        App.getInstance().getActivityComponent().inject(this);
    }


}
