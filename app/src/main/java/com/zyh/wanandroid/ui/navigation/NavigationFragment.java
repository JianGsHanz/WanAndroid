package com.zyh.wanandroid.ui.navigation;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.base.BaseMvpFragment;
import com.common.util.LogUtils;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.NavigationResult;
import com.zyh.wanandroid.ui.navigation.adapter.LeftAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : zyh
 * Date : 2018/12/17
 * Description :
 */
public class NavigationFragment extends BaseMvpFragment<NavigationFPresenter> implements NavigationConstract.view {

    @BindView(R.id.left_rv)
    RecyclerView leftRv;
    @BindView(R.id.right_rv)
    RecyclerView rightRv;
    Unbinder unbinder;
    private List<NavigationResult.DataBean> navigationList = new ArrayList<>();
    private LeftAdapter leftAdapter;

    @Inject
    public NavigationFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        leftRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        leftAdapter = new LeftAdapter(R.layout.item_left_recycler_view, navigationList);
        leftRv.setAdapter(leftAdapter);

        mPresenter.loadData();
    }

    @Override
    public void getNavigationSuccess(@NotNull NavigationResult navigationResult) {
        leftAdapter.addData(navigationResult.getData());
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
