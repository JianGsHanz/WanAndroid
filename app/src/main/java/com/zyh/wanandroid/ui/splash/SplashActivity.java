package com.zyh.wanandroid.ui.splash;

import com.common.base.IBaseMvpActivity;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.ui.LBaseActivity;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.ui.main.MainActivity;

import javax.inject.Inject;

/**
 * author : zyh
 * Date : 2018/11/29
 * Description :
 */
public class SplashActivity extends LBaseActivity implements IBaseMvpActivity<SplashPresenter>,SplashContract.view {

    @Inject
    SplashPresenter presenter;

    @Override
    public void initViewAndEvent() {
        presenter.loadData();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.loadData();
    }

    @Override
    public void result() {
        startActivity(MainActivity.class,null);
        finish();
    }
}
