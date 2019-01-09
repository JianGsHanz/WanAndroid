package com.zyh.wanandroid.ui.category.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.BaseMvpFragment;
import com.common.util.ToastUtils;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.CategoryListResult;
import com.zyh.wanandroid.ui.category.adapter.CategoryListAdapter;
import com.zyh.wanandroid.ui.main.MainFragment;
import com.zyh.wanandroid.ui.web.WebFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Time:2018/12/19
 * Author:ZYH
 * Description:项目分类列表
 */
public class CategoryListFragment extends BaseMvpFragment<CategoryListFPresenter> implements CategoryListConstract.view,
        BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    Unbinder unbinder;
    @BindView(R.id.category_rv)
    RecyclerView categoryRv;
    @BindView(R.id.category_swipe)
    SwipeRefreshLayout categorySwipe;
    private int id;
    private CategoryListAdapter categoryListAdapter;
    private List<CategoryListResult.DataBean.DatasBean> dataList = new ArrayList<>();

    @Inject
    public CategoryListFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_list;
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
        categorySwipe.setColorSchemeColors(Color.rgb(0, 0, 0));
        categorySwipe.setRefreshing(true);

        categoryRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryListAdapter = new CategoryListAdapter(R.layout.item_category_list_rv, dataList);
        categoryRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        categoryRv.setAdapter(categoryListAdapter);

        mPresenter.autoRefresh(id);

        categorySwipe.setOnRefreshListener(this);
        categoryListAdapter.setOnLoadMoreListener(this);
        categoryListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((MainFragment) getParentFragment().getParentFragment())
                        .goFragment(WebFragment.newInstance(dataList.get(position).getLink(),
                                dataList.get(position).getTitle(), dataList.get(position).getId(),
                                dataList.get(position).isCollect(),-1),-1);

            }
        });

    }

    public static CategoryListFragment newInstance(int id) {
        CategoryListFragment categoryListFragment = new CategoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        categoryListFragment.setArguments(bundle);
        return categoryListFragment;
    }

    @Override
    public void getCategoryListSuccess(@NotNull CategoryListResult.DataBean dataResult, boolean isRefresh) {
        categorySwipe.setRefreshing(false);
        if (isRefresh) {
            dataList = dataResult.getDatas();
            categoryListAdapter.setNewData(dataList);
        } else {
            dataList.addAll(dataResult.getDatas());
            categoryListAdapter.replaceData(dataList);
            categoryListAdapter.loadMoreComplete();
        }

    }

    @Override
    public void getCategoryListFail(@NotNull String errorMsg) {
        if (TextUtils.isEmpty(errorMsg))
            categoryListAdapter.loadMoreEnd();
        else
            ToastUtils.showShortToast(errorMsg);

    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPresenter.autoRefresh(id);
    }

    //上拉加载
    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        id = getArguments().getInt("id");
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
