package com.zyh.wanandroid.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.BaseMvpFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.BannerResult;
import com.zyh.wanandroid.model.HomeResult;
import com.zyh.wanandroid.ui.home.adapter.HomeRvAdapter;
import com.zyh.wanandroid.utils.GlideImageLoader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
public class HomeFragment extends BaseMvpFragment<HomeFPresenter> implements HomeFConstract.view,SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{


    Unbinder unbinder;
    @BindView(R.id.home_recycler_view)
    RecyclerView homeRecyclerView;
    @BindView(R.id.home_swipe_layout)
    SwipeRefreshLayout homeSwipeLayout;
    private List<HomeResult.DatasBean> homeResult = new ArrayList<>();
    private List<String> linkList = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private Banner banner;
    private HomeRvAdapter homeRvAdapter;

    @Inject
    public HomeFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {

        ConstraintLayout bannerView = (ConstraintLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_banner, null);
        banner = bannerView.findViewById(R.id.home_banner);
        bannerView.removeView(banner);
        bannerView.addView(banner);

        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeRvAdapter = new HomeRvAdapter(R.layout.item_home_recycler_view, homeResult);
        homeRvAdapter.addHeaderView(bannerView);

        homeSwipeLayout.setColorSchemeColors(Color.rgb(50, 233, 189));
        homeSwipeLayout.setRefreshing(true);
        mPresenter.loadData();
        mPresenter.autoRefresh();

        homeSwipeLayout.setOnRefreshListener(this);
        homeRvAdapter.setOnLoadMoreListener(this);
        homeRecyclerView.setAdapter(homeRvAdapter);
    }

    @Override
    public void getBannerSuccess(@NotNull List<BannerResult> listBaseResult) {
        linkList.clear();
        imageList.clear();
        titleList.clear();
        for (BannerResult dataBean : listBaseResult) {
            linkList.add(dataBean.getUrl());
            imageList.add(dataBean.getImagePath());
            titleList.add(dataBean.getTitle());
        }
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setImageLoader(new GlideImageLoader())
                .setImages(imageList)
                .setBannerAnimation(Transformer.Default)
                .setBannerTitles(titleList)
                .isAutoPlay(true)
                .setDelayTime(2500)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();

    }
    @Override
    public void getHomeListSuccess(@NotNull HomeResult homeDatasResult,boolean isRefresh) {
        homeSwipeLayout.setRefreshing(false);
        if (isRefresh){
            homeResult = homeDatasResult.getDatas();
            homeRvAdapter.replaceData(homeResult);
        }else {
            homeResult.addAll(homeDatasResult.getDatas());
            homeRvAdapter.addData(homeResult);
        }
    }

    @Override
    public void onRefresh() {
        homeSwipeLayout.setRefreshing(true);
        mPresenter.loadData();
        mPresenter.autoRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    public void scrollToTop() {
        homeRecyclerView.smoothScrollToPosition(0);
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



}
