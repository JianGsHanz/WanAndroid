package com.zyh.wanandroid.ui.navigation;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.BaseMvpFragment;
import com.common.util.LogUtils;
import com.common.util.ToastUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.NavigationResult;
import com.zyh.wanandroid.ui.main.MainFragment;
import com.zyh.wanandroid.ui.navigation.adapter.LeftAdapter;
import com.zyh.wanandroid.ui.web.WebFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : zyh
 * Date : 2018/12/17
 * Description :导航
 */
public class NavigationFragment extends BaseMvpFragment<NavigationFPresenter> implements NavigationConstract.view {

    @BindView(R.id.left_rv)
    RecyclerView leftRv;
    Unbinder unbinder;
    @BindView(R.id.right_fl)
    TagFlowLayout rightFl;
    private List<NavigationResult.DataBean> dataList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private List<String> urlList = new ArrayList<>();
    private List<Integer> idList = new ArrayList<>();
    private LeftAdapter leftAdapter;
    private NavigationResult navigationResult;

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
        leftAdapter = new LeftAdapter(R.layout.item_left_recycler_view, dataList);
        leftRv.setAdapter(leftAdapter);

        leftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                leftAdapter.setPosition(position);
                setFlowLayout(position);

            }
        });
    }

    private void setFlowLayout(int position) {
        List<NavigationResult.DataBean.ArticlesBean> articles = navigationResult.getData().get(position).getArticles();
        titleList.clear();
        for (int i = 0; i < articles.size(); i++) {
            titleList.add(articles.get(i).getTitle());
            urlList.add(articles.get(i).getLink());
            idList.add(articles.get(i).getId());
        }
        rightFl.setAdapter(new TagAdapter(titleList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_tv,
                        rightFl, false);
                tv.setText((String) o);
                return tv;
            }
        });

        rightFl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ((MainFragment)getParentFragment()).goFragment(WebFragment.newInstance(urlList.get(position),titleList.get(position),idList.get(position)),-1);
                return true;
            }
        });
    }

    @Override
    public void getNavigationSuccess(@NotNull NavigationResult navigationResult) {
        this.navigationResult = navigationResult;
        leftAdapter.addData(navigationResult.getData());
        setFlowLayout(0);
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
