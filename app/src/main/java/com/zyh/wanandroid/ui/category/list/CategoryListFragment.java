package com.zyh.wanandroid.ui.category.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.base.BaseMvpFragment;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Time:2018/12/19
 * Author:ZYH
 * Description:
 */
public class CategoryListFragment extends BaseMvpFragment<CategoryListFPresenter> implements CategoryListConstract.view {

    @BindView(R.id.knowledge_rv)
    RecyclerView knowledgeRv;
    @BindView(R.id.knowledge_swipe)
    SwipeRefreshLayout knowledgeSwipe;
    Unbinder unbinder;
    @BindView(R.id.category_rv)
    RecyclerView categoryRv;
    @BindView(R.id.category_swipe)
    SwipeRefreshLayout categorySwipe;
    private int id;

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

    }

    public static CategoryListFragment newInstance(int id) {
        CategoryListFragment categoryListFragment = new CategoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        categoryListFragment.setArguments(bundle);
        return categoryListFragment;
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
