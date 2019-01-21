package com.zyh.wanandroid.ui.category.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.util.SystemUtil;
import com.common.util.ToastUtils;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.base.LBaseMvpFragment;
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
public class CategoryListFragment extends LBaseMvpFragment<CategoryListFPresenter> implements CategoryListConstract.view,
        BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    Unbinder unbinder;
    @BindView(R.id.category_rv)
    ShimmerRecyclerView categoryRv;
    @BindView(R.id.category_swipe)
    SwipeRefreshLayout categorySwipe;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.normal_view)
    ConstraintLayout normalView;
    @BindView(R.id.title_layout)
    AppBarLayout titleLayout;
    private int id;
    private CategoryListAdapter categoryListAdapter;
    private List<CategoryListResult.DataBean.DatasBean> dataList = new ArrayList<>();
    private String keyWord;

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
        if (TextUtils.isEmpty(keyWord)) {
            titleLayout.setVisibility(View.GONE);
        } else {
            titleLayout.setVisibility(View.VISIBLE);
            titleName.setText("搜索结果");
            search.setVisibility(View.INVISIBLE);
            ConstraintLayout.LayoutParams lp =
                    new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.setMargins(0, SystemUtil.dp2px(getActivity(),48),0,0);
            categorySwipe.setLayoutParams(lp);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        categorySwipe.setColorSchemeColors(Color.rgb(0, 0, 0));

        categoryRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryListAdapter = new CategoryListAdapter(R.layout.item_category_list_rv, dataList);
        categoryListAdapter.openLoadAnimation();
        categoryRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        categoryRv.setAdapter(categoryListAdapter);
        categoryRv.showShimmerAdapter();

        mPresenter.autoRefresh(id, keyWord);

        categorySwipe.setOnRefreshListener(this);
        categoryListAdapter.setOnLoadMoreListener(this);
        categoryListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.isEmpty(keyWord))
                    ((MainFragment) getParentFragment().getParentFragment())
                            .goFragment(WebFragment.newInstance(dataList.get(position).getLink(),
                                    dataList.get(position).getTitle(), dataList.get(position).getId(),
                                    dataList.get(position).isCollect(), -1), -1);
                else
                    ((MainFragment) getParentFragment())
                            .goFragment(WebFragment.newInstance(dataList.get(position).getLink(),
                                    dataList.get(position).getTitle(), dataList.get(position).getId(),
                                    dataList.get(position).isCollect(), -1), -1);

            }
        });

    }

    public static CategoryListFragment newInstance(int id, String k) {
        CategoryListFragment categoryListFragment = new CategoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("keyword", k);
        categoryListFragment.setArguments(bundle);
        return categoryListFragment;
    }

    @Override
    public void getCategoryListSuccess(@NotNull CategoryListResult.DataBean dataResult, boolean isRefresh) {
        categoryRv.hideShimmerAdapter();
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
        else if (!TextUtils.isEmpty(keyWord) && categoryListAdapter.getData().size() == 0) {
            ToastUtils.showShortToast(errorMsg);
            _mActivity.onBackPressed();
        } else
            ToastUtils.showShortToast(errorMsg);



    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPresenter.autoRefresh(id, keyWord);
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
        keyWord = getArguments().getString("keyword");
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
