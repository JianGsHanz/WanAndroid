package com.zyh.wanandroid.ui.main;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.base.BaseFragment;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.ui.article.ArticleFragment;
import com.zyh.wanandroid.ui.category.CategoryFragment;
import com.zyh.wanandroid.ui.home.HomeFragment;
import com.zyh.wanandroid.ui.mine.MineFragment;
import com.zyh.wanandroid.utils.view.BottomBar;
import com.zyh.wanandroid.utils.view.BottomBarTab;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * author : zyh
 * Date : 2018/12/4
 * Description :
 */
public class MainFragment extends BaseFragment implements ISupportFragment {
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
    Unbinder unbinder;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private SupportFragment[] fragments = new SupportFragment[4];

    @Inject
    public MainFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments[0] = homeFragment;
        fragments[1] = articleFragment;
        fragments[2] = categoryFragment;
        fragments[3] = mineFragment;

        loadMultipleRootFragment(R.id.frame_layout, 0,
                homeFragment,
                articleFragment,
                categoryFragment,
                mineFragment);
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTransparent)));

        bottomBar
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_home, "首页"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_article, "文章"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_category, "分类"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_me, "我的"));

        bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onTabSelected(int position, int prePosition) {
                switch (position) {
                    case 0:
                        titleName.setText("首页");
                        fab.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        titleName.setText("文章");
                        fab.setVisibility(View.GONE);
                        break;
                    case 2:
                        titleName.setText("分类");
                        fab.setVisibility(View.GONE);
                        break;
                    case 3:
                        titleName.setText("我的");
                        fab.setVisibility(View.GONE);
                        break;
                }
                showHideFragment(fragments[position], fragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        EventBus.getDefault().post("event");
    }

    public void goFragment(ISupportFragment fragment) {
        start(fragment);
    }
}
