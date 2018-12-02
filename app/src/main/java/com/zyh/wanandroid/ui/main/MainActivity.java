package com.zyh.wanandroid.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.common.base.BaseActivity;
import com.common.base.IBaseMvpActivity;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.ui.article.ArticleFragment;
import com.zyh.wanandroid.ui.category.CategoryFragment;
import com.zyh.wanandroid.ui.home.HomeFragment;
import com.zyh.wanandroid.ui.mine.MineFragment;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
public class MainActivity extends BaseActivity implements IBaseMvpActivity<MainPresenter>, MainContract.view,
        BottomNavigationView.OnNavigationItemSelectedListener {


    @Inject
    MainPresenter presenter;
    @Inject
    HomeFragment homeFragment;
    @Inject
    ArticleFragment articleFragment;
    @Inject
    CategoryFragment categoryFragment;
    @Inject
    MineFragment mineFragment;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.navigation_bottom)
    BottomNavigationView navigationBottom;
    @BindView(R.id.title_name)
    TextView titleName;

    private long TOUCH_TIME = 0;  //点击返回键时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMultipleRootFragment(R.id.frame_layout, 0,
                homeFragment,
                articleFragment,
                categoryFragment,
                mineFragment);
    }

    @Override
    public void initViewAndEvent() {
        navigationBottom.setOnNavigationItemSelectedListener(this);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.navigation_home:
                showHideFragment(homeFragment);
                titleName.setText("首页");
                break;
            case R.id.navigation_article:
                showHideFragment(articleFragment);
                titleName.setText("分类");
                break;
            case R.id.navigation_category:
                showHideFragment(categoryFragment);
                titleName.setText("项目");
                break;
            case R.id.navigation_my:
                showHideFragment(mineFragment);
                titleName.setText("我的");
                break;
        }
        return true;
    }
}
