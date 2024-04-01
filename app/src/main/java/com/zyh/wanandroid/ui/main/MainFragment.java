package com.zyh.wanandroid.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.BaseFragment;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.ui.category.CategoryFragment;
import com.zyh.wanandroid.ui.category.list.CategoryListFragment;
import com.zyh.wanandroid.ui.home.HomeFragment;
import com.zyh.wanandroid.ui.knowledge.knowledgeFragment;
import com.zyh.wanandroid.ui.mine.MineFragment;
import com.zyh.wanandroid.ui.navigation.NavigationFragment;
import com.zyh.wanandroid.utils.event.MsgEvent;
import com.zyh.wanandroid.utils.view.BottomBar;
import com.zyh.wanandroid.utils.view.BottomBarTab;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class MainFragment extends BaseFragment implements ISupportFragment, IOnSearchClickListener {
    @Inject
    HomeFragment homeFragment;
    @Inject
    NavigationFragment navigationFragment;
    @Inject
    knowledgeFragment knowledgeFragment;
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
    @BindView(R.id.search)
    ImageView search;

    private SupportFragment[] fragments = new SupportFragment[5];
    private SearchFragment searchFragment;

    @Inject
    public MainFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        fragments[0] = homeFragment;
        fragments[1] = navigationFragment;
        fragments[2] = knowledgeFragment;
        fragments[3] = categoryFragment;
        fragments[4] = mineFragment;

        loadMultipleRootFragment(R.id.frame_layout, 0,
                homeFragment,
                navigationFragment,
                knowledgeFragment,
                categoryFragment,
                mineFragment);
        searchFragment = SearchFragment.newInstance();
        searchFragment.setOnSearchClickListener(this);
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        titleName.setText("首页");

        bottomBar
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_home, "首页"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_navigation, "导航"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_article, "知识"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_category, "分类"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_me, "我的"));

        bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onTabSelected(int position, int prePosition) {
                switch (position) {
                    case 0:
                        titleName.setText("首页");
                        fab.setVisibility(View.GONE);
                        break;
                    case 1:
                        titleName.setText("导航");
                        fab.setVisibility(View.GONE);
                        break;
                    case 2:
                        titleName.setText("知识");
                        fab.setVisibility(View.GONE);
                        break;
                    case 3:
                        titleName.setText("分类");
                        fab.setVisibility(View.GONE);
                        break;
                    case 4:
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

    @SuppressLint("RestrictedApi")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent msgEvent) {
        if ((int) msgEvent.getO() == 1) {
            fab.setVisibility(View.VISIBLE);
            fab.animate().scaleX(1F).scaleY(1F).setDuration(500).start();
        }else {
            fab.animate().scaleX(0F).scaleY(0F).setDuration(500).start();
        }
    }

    @OnClick({R.id.search, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                searchFragment.showFragment(getChildFragmentManager(), SearchFragment.TAG);
                break;
            case R.id.fab:
                EventBus.getDefault().post("event");
                break;
        }
    }

    @Override
    public void OnSearchClick(String keyword) {
        goFragment(CategoryListFragment.newInstance(-1,keyword),-1);
    }

    public void goFragment(ISupportFragment fragment, int requestCode) {
        if (requestCode == -1)
            start(fragment);
        else
            startForResult(fragment, requestCode);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
