package com.zyh.wanandroid.ui.knowledge.article;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.BaseMvpFragment;
import com.common.util.LogUtils;
import com.common.util.ToastUtils;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.KnowledgeListResult;
import com.zyh.wanandroid.ui.knowledge.adapter.KnowledgeListAdapter;
import com.zyh.wanandroid.ui.main.MainFragment;
import com.zyh.wanandroid.ui.web.WebFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class KnowledgeListFragment extends BaseMvpFragment<KnowledgeListFPresenter> implements KnowledgeListConstract.view, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.knowledge_rv)
    RecyclerView knowledgeRv;
    @BindView(R.id.knowledge_swipe)
    SwipeRefreshLayout knowledgeSwipe;
    Unbinder unbinder;
    private int id;
    private KnowledgeListAdapter knowledgeListAdapter;
    private List<KnowledgeListResult.DataBean.DatasBean> dataList = new ArrayList<>();

    @Inject
    public KnowledgeListFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_list;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        knowledgeSwipe.setColorSchemeColors(Color.rgb(50, 233, 189));
        knowledgeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        knowledgeListAdapter = new KnowledgeListAdapter(R.layout.item_knowledge_list_rv,dataList);
        knowledgeRv.setAdapter(knowledgeListAdapter);

        knowledgeSwipe.setOnRefreshListener(this);
        knowledgeListAdapter.setOnLoadMoreListener(this);
        LogUtils.e("this = "+this+", "+id);
        mPresenter.autoRefresh(id);

        knowledgeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KnowledgeListResult.DataBean.DatasBean data = (KnowledgeListResult.DataBean.DatasBean) adapter.getData().get(position);
                ((MainFragment)getParentFragment()).goFragment(WebFragment.newInstance(
                        data.getLink(),data.getTitle(),data.getId()),-1);
                ToastUtils.showShortToast(position);
            }
        });
    }

    @Override
    public void getKnowledgeListSuccess(@NotNull KnowledgeListResult.DataBean dataResult,boolean isRefresh) {
        LogUtils.e("data before size = "+dataResult.getDatas().size());
        knowledgeSwipe.setRefreshing(false);
        if (isRefresh) { //刷新
            dataList = dataResult.getDatas();
            knowledgeListAdapter.replaceData(dataList);
        }else { //加载更多
            LogUtils.e("list before  size == "+dataList.size());
            dataList.addAll(dataResult.getDatas());
            LogUtils.e("list after  size == "+dataList.size());
            knowledgeListAdapter.addData(dataList);
        }

    }

    @Override
    public void getKnowledgeListFail(@NotNull String errorMsg) {
        if (TextUtils.isEmpty(errorMsg))
            knowledgeListAdapter.loadMoreEnd();
        else
            ToastUtils.showShortToast(errorMsg);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        knowledgeSwipe.setRefreshing(true);
        mPresenter.autoRefresh(id);
    }
    //上拉加载
    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    public static KnowledgeListFragment newInstance(int id) {
        KnowledgeListFragment knowledgeListFragment = new KnowledgeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        knowledgeListFragment.setArguments(bundle);
        return knowledgeListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();
        id = arguments.getInt("id");
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
