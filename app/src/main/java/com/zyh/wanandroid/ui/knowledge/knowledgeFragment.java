package com.zyh.wanandroid.ui.knowledge;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.base.BaseMvpFragment;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.ArticleResult;
import com.zyh.wanandroid.ui.knowledge.adapter.ArticleRvAdapter;
import com.zyh.wanandroid.ui.knowledge.article.KnowledgeArticleFragment;
import com.zyh.wanandroid.ui.main.MainFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :知识
 */
public class knowledgeFragment extends BaseMvpFragment<knowledgeFPresenter> implements knowledgeFConstract.view, BaseQuickAdapter.OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.article_rv)
    RecyclerView articleRv;
    private ArrayList<ArticleResult.DataBean> dataList = new ArrayList<>();
    private ArticleRvAdapter rvAdapter;

    @Inject
    public knowledgeFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        articleRv.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAdapter = new ArticleRvAdapter(R.layout.item_article_recycler_view, dataList);
        articleRv.setAdapter(rvAdapter);

        rvAdapter.setOnItemClickListener(this);
    }

    @Override
    public void getAricleSuccess(@NotNull List<? extends ArticleResult.DataBean> data) {
        rvAdapter.addData(data);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ArticleResult.DataBean children = ((ArticleResult.DataBean) adapter.getData().get(position));
        ((MainFragment)getParentFragment()).goFragment(KnowledgeArticleFragment.newInstance(children.getName(),children.getChildren()),-1);
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
