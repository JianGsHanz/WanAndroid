package com.zyh.wanandroid.ui.category;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyh.wanandroid.App;
import com.zyh.wanandroid.base.LBaseMvpFragment;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.CategoryResult;
import com.zyh.wanandroid.ui.category.adapter.FPagerAdapter;

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
 * Description :项目分类
 */
public class CategoryFragment extends LBaseMvpFragment<CategoryFPresenter> implements CategoryFConstract.view {

    Unbinder unbinder;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<Integer> idList = new ArrayList<>();

    @Inject
    public CategoryFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        tabLayout.setupWithViewPager(viewPager,false);
    }

    @Override
    public void getCategorySuccess(@NotNull List<? extends CategoryResult.DataBean> dataResult) {
        for (int i = 0; i < dataResult.size(); i++){
            titleList.add(dataResult.get(i).getName());
            idList.add(dataResult.get(i).getId());
        }
        FPagerAdapter fPagerAdapter = new FPagerAdapter(getChildFragmentManager(),titleList,idList);
        viewPager.setAdapter(fPagerAdapter);
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
