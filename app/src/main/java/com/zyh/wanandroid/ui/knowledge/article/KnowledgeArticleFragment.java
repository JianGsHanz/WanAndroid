package com.zyh.wanandroid.ui.knowledge.article;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyh.wanandroid.App;
import com.zyh.wanandroid.base.LBaseMvpFragment;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.ArticleResult;
import com.zyh.wanandroid.ui.knowledge.adapter.FmPagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : zyh
 * Date : 2018/12/18
 * Description :知识文章
 */
public class KnowledgeArticleFragment extends LBaseMvpFragment<KnowledgeArticleFPresenter> implements KnowledgeArticleFConstract.view {

    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<Integer> idList = new ArrayList<>();
    private ArrayList<ArticleResult.DataBean.ChildrenBean> list;
    private String name;

    @Inject
    public KnowledgeArticleFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_article;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        titleName.setText(name);
        for (int i = 0; i <list.size() ; i++) {
            titleList.add(list.get(i).getName());
            idList.add(list.get(i).getId());
        }
        tabLayout.setupWithViewPager(viewPager,false);
        FmPagerAdapter fmPagerAdapter = new FmPagerAdapter(getChildFragmentManager(), titleList,idList);
        viewPager.setAdapter(fmPagerAdapter);

    }

    public static KnowledgeArticleFragment newInstance(String name, List<ArticleResult.DataBean.ChildrenBean> children) {
        KnowledgeArticleFragment knowledgeArticleFragment = new KnowledgeArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putSerializable("list", (Serializable) children);
        knowledgeArticleFragment.setArguments(bundle);
        return knowledgeArticleFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();
        name = arguments.getString("name");
        list = (ArrayList<ArticleResult.DataBean.ChildrenBean>) arguments.getSerializable("list");
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
