package com.zyh.wanandroid.ui.collect;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.BaseMvpFragment;
import com.common.util.PrefsUtils;
import com.common.util.ToastUtils;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.CollectResult;
import com.zyh.wanandroid.utils.event.CollectEvent;
import com.zyh.wanandroid.ui.collect.adapter.CollectAdapter;
import com.zyh.wanandroid.ui.login.LoginRegisterFragment;
import com.zyh.wanandroid.ui.web.WebFragment;

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
 * Date : 2018/12/27
 * Description :
 */
public class CollectFragment extends BaseMvpFragment<CollectFPresenter> implements CollectFConstract.view, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.collect_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.collect_swipe_layout)
    SwipeRefreshLayout swipeLayout;
    Unbinder unbinder;
    @BindView(R.id.title_name)
    TextView titleName;
    private CollectAdapter adapter;
    private List<CollectResult.DataBean.DatasBean> list = new ArrayList<>();

    @Inject
    public CollectFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        titleName.setText("收藏");
        swipeLayout.setColorSchemeColors(Color.rgb(32, 123, 255));
        swipeLayout.setRefreshing(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new CollectAdapter(list);
        recyclerView.setAdapter(adapter);

        mPresenter.onRefresh();

        swipeLayout.setOnRefreshListener(this);
        adapter.setOnLoadMoreListener(this);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CollectResult.DataBean.DatasBean data= (CollectResult.DataBean.DatasBean) adapter.getData().get(position);
                start(WebFragment.newInstance(data.getLink(),data.getTitle(),
                                data.getId(),true,data.getOriginId()));
            }
        });
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    //上拉加载
    @Override
    public void onLoadMoreRequested() {
        mPresenter.onLoadMore();
    }

    @Override
    public void getCollectListSuccess(@NotNull CollectResult.DataBean data, boolean isRefresh) {
        swipeLayout.setRefreshing(false);
        if (isRefresh) {
            list = data.getDatas();
            adapter.setNewData(list);
        } else {
            list.addAll(data.getDatas());
            adapter.replaceData(list);
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void getCollectListFail(@NotNull String error) {
        if (!TextUtils.isEmpty(error)){
            ToastUtils.showShortToast(error);
        }
        swipeLayout.setRefreshing(false);
        adapter.loadMoreEnd();

    }

    @Override
    public void isEmptyLayout() {
        swipeLayout.setRefreshing(false);
        adapter.setEmptyView(getLayoutInflater()
                .inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false));
    }

    @Override
    public void unOverdue() {
        ToastUtils.showShortToast("身份过期，请重新登录");
        PrefsUtils.getInstance().remove("userName");
        EventBus.getDefault().post("loginOut");
        showHideFragment(LoginRegisterFragment.newInstance(),this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void unCollect(CollectEvent collectEvent){
        if (collectEvent.getOrginId() > 0){
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getOriginId() == collectEvent.getOrginId()){
                    list.remove(i);
                }
            }
            adapter.notifyDataSetChanged();
        }
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
