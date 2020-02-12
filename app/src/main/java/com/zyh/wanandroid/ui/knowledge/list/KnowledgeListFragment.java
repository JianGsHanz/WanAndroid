package com.zyh.wanandroid.ui.knowledge.list;

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
import com.common.util.ToastUtils;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.base.LBaseMvpFragment;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.KnowledgeListResult;
import com.zyh.wanandroid.ui.knowledge.adapter.KnowledgeListAdapter;
import com.zyh.wanandroid.ui.knowledge.article.KnowledgeArticleFragment;
import com.zyh.wanandroid.ui.web.WebFragment;

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
 * Description :知识文章列表
 */
public class KnowledgeListFragment extends LBaseMvpFragment<KnowledgeListFPresenter> implements KnowledgeListConstract.view, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
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
        knowledgeSwipe.setColorSchemeColors(Color.rgb(0, 0, 0));
        knowledgeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        knowledgeListAdapter = new KnowledgeListAdapter(R.layout.item_knowledge_list_rv,dataList);
        knowledgeListAdapter.openLoadAnimation();
        knowledgeRv.setAdapter(knowledgeListAdapter);

        knowledgeSwipe.setOnRefreshListener(this);
        knowledgeListAdapter.setOnLoadMoreListener(this);
        mPresenter.autoRefresh(id);

        knowledgeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KnowledgeListResult.DataBean.DatasBean data = (KnowledgeListResult.DataBean.DatasBean) adapter.getData().get(position);
                ((KnowledgeArticleFragment)getParentFragment()).start(WebFragment.newInstance(
                        data.getLink(),data.getTitle(),data.getId(),data.isCollect(),-1));
            }
        });
    }

    @Override
    public void getKnowledgeListSuccess(@NotNull KnowledgeListResult.DataBean dataResult,boolean isRefresh) {
        knowledgeSwipe.setRefreshing(false);
        if (isRefresh) { //刷新
            dataList = dataResult.getDatas();
            knowledgeListAdapter.setNewData(dataList);
        }else { //加载更多
            dataList.addAll(dataResult.getDatas());
            knowledgeListAdapter.replaceData(dataList);
            knowledgeListAdapter.loadMoreComplete();
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
