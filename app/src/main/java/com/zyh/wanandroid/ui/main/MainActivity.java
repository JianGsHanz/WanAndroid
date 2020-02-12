package com.zyh.wanandroid.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.common.base.IBaseMvpActivity;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.base.LBaseActivity;

import javax.inject.Inject;

/**
 * author : zyh
 * Date : 2019/1/30
 * Description :
 */
public class MainActivity extends LBaseActivity implements IBaseMvpActivity<MainPresenter>, MainContract.view {


    @Inject
    MainPresenter presenter;
    @Inject
    MainFragment mainFragment;

    private long TOUCH_TIME = 0;  //点击返回键时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadRootFragment(R.id.normal_view,mainFragment);

    }

    @Override
    public void initViewAndEvent() {
    }

    @Override
    public MainPresenter getPresenter() {
        return presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.wan_activity_main;
    }

    @Override
    public void initInject() {
        App.getInstance().getActivityComponent().inject(this);
    }


    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if ((System.currentTimeMillis() - TOUCH_TIME) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                TOUCH_TIME = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }


}
