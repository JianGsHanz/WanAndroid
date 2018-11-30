package com.zyh.wanandroid.ui.main;

import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import com.common.base.IBaseMvpActivity;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.ui.LBaseActivity;
import com.zyh.wanandroid.ui.home.HomeFragment;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
public class MainActivity extends LBaseActivity implements IBaseMvpActivity<MainPresenter>, MainContract.view {
    @Inject
    MainPresenter presenter;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    private long TOUCH_TIME = 0;  //点击返回键时间

    @Override
    public void initViewAndEvent() {
         if (findFragment(HomeFragment.class)!=null) {
//             loadMultipleRootFragment();
         }
    }

    @Override
    public MainPresenter getPresenter() {
        return presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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
