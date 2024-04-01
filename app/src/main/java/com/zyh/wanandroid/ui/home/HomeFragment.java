package com.zyh.wanandroid.ui.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.base.LBaseMvpFragment;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.BannerResult;
import com.zyh.wanandroid.model.HomeResult;
import com.zyh.wanandroid.ui.home.adapter.HomeRvAdapter;
import com.zyh.wanandroid.ui.main.MainFragment;
import com.zyh.wanandroid.ui.web.WebFragment;
import com.zyh.wanandroid.utils.GlideImageLoader;
import com.zyh.wanandroid.utils.event.CollectEvent;
import com.zyh.wanandroid.utils.event.MsgEvent;

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

/**
 * author : zyh
 * Date : 2019/1/30
 * Description :首页
 */
public class HomeFragment extends LBaseMvpFragment<HomeFPresenter> implements HomeFConstract.view, SwipeRefreshLayout.OnRefreshListener,
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initViewAndEvent() {
        showLoading();
        ConstraintLayout bannerView = (ConstraintLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_banner, null);
        banner = bannerView.findViewById(R.id.home_banner);
        bannerView.removeView(banner);
        bannerView.addView(banner);

        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeRvAdapter = new HomeRvAdapter(R.layout.item_home_recycler_view, homeResult);
        homeRvAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        homeRvAdapter.addHeaderView(bannerView);

        homeSwipeLayout.setColorSchemeColors(Color.rgb(0, 0, 0));
//        homeSwipeLayout.setRefreshing(true);
        homeRecyclerView.setAdapter(homeRvAdapter);

        mPresenter.autoRefresh();

        homeSwipeLayout.setOnRefreshListener(this);
        homeRvAdapter.setOnLoadMoreListener(this);
        homeRvAdapter.setOnItemClickListener(this);
        homeRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
               if (dy == 0){
                    EventBus.getDefault().post(new MsgEvent(0));
                }else if (dy < 0){
                   EventBus.getDefault().post(new MsgEvent(1));
               }
            }
        });
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
                ((MainFragment) getParentFragment()).goFragment(WebFragment.newInstance(datas.getUrl(),
                        datas.getTitle(),-1,false,-1),-1);
            }
        });

    }

    @Override
    public void getHomeListSuccess(@NotNull HomeResult homeDatasResult, boolean isRefresh) {
        showNormal();
        homeSwipeLayout.setRefreshing(false);
        if (isRefresh) {
            homeResult = homeDatasResult.getDatas();
            homeRvAdapter.setNewData(homeResult);
        } else {
            homeResult.addAll(homeDatasResult.getDatas());
            homeRvAdapter.replaceData(homeResult);
            homeRvAdapter.loadMoreComplete();
        }
    }

    @Override
    public void getHomeListFail(@NotNull String error) {
        showError(error);
    }

    @Override
    public void reLoad() {
        mPresenter.autoRefresh();
    }

    @Override
    public void onRefresh() {
        EventBus.getDefault().post(new MsgEvent(0));
        homeSwipeLayout.setRefreshing(true);
        mPresenter.autoRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String o){
        if (o.equals("event")) {
            EventBus.getDefault().post(new MsgEvent(0));
            if (o.equals("event"))
                homeRecyclerView.scrollToPosition(0);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCollect(CollectEvent collectEvent){
        if (collectEvent.getOrginId() < 0 ) {
            for (int i = 0; i < homeResult.size(); i++) {
                if (homeResult.get(i).getId() == collectEvent.getId()) {
                    homeResult.get(i).setCollect(true);
                }
            }
            homeRvAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HomeResult.DatasBean datas = (HomeResult.DatasBean) adapter.getData().get(position);
        ((MainFragment) getParentFragment()).goFragment(WebFragment.newInstance(datas.getLink(),
                datas.getTitle(),datas.getId(),datas.isCollect(),-1),-1);
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
