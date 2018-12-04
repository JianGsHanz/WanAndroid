package com.zyh.wanandroid.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.BaseMvpFragment;
import com.common.util.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.BannerResult;
import com.zyh.wanandroid.model.HomeResult;
import com.zyh.wanandroid.ui.home.adapter.HomeRvAdapter;
import com.zyh.wanandroid.ui.main.MainActivity;
import com.zyh.wanandroid.ui.main.MainFragment;
import com.zyh.wanandroid.ui.web.WebFragment;
import com.zyh.wanandroid.utils.GlideImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
public class HomeFragment extends BaseMvpFragment<HomeFPresenter> implements HomeFConstract.view, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener{


    @BindView(R.id.home_recycler_view)
    RecyclerView homeRecyclerView;
    @BindView(R.id.home_swipe_layout)
    SwipeRefreshLayout homeSwipeLayout;

    Unbinder unbinder;
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
        homeRecyclerView.setAdapter(homeRvAdapter);

        mPresenter.loadData();
        mPresenter.autoRefresh();

        homeSwipeLayout.setOnRefreshListener(this);
        homeRvAdapter.setOnLoadMoreListener(this);
        homeRvAdapter.setOnItemClickListener(this);
    }

    @Override
    public void getBannerSuccess(@NotNull final List<BannerResult> listBaseResult) {
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

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerResult datas = listBaseResult.get(position);
                ((MainFragment) getParentFragment()).goFragment(WebFragment.newInstance(datas.getUrl(),datas.getTitle(),datas.getId()));
            }
        });

    }

    @Override
    public void getHomeListSuccess(@NotNull HomeResult homeDatasResult, boolean isRefresh) {
        homeSwipeLayout.setRefreshing(false);
        if (isRefresh) {
            homeResult = homeDatasResult.getDatas();
            homeRvAdapter.replaceData(homeResult);
        } else {
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String o){
        homeRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HomeResult.DatasBean datas = (HomeResult.DatasBean) adapter.getData().get(position);
        ((MainFragment) getParentFragment()).goFragment(WebFragment.newInstance(datas.getLink(),datas.getTitle(),datas.getId()));
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
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }
}
