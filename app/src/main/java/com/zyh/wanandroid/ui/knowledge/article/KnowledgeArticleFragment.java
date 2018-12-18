package com.zyh.wanandroid.ui.knowledge.article;

import android.os.Bundle;

import com.common.base.BaseMvpFragment;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.ArticleResult;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

/**
 * author : zyh
 * Date : 2018/12/18
 * Description :
 */
public class KnowledgeArticleFragment extends BaseMvpFragment<KnowledgeArticleFPresenter> implements KnowledgeArticleFConstract.view{

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

    }

    public static KnowledgeArticleFragment newInstance(String name, List<ArticleResult.DataBean.ChildrenBean> children){
        KnowledgeArticleFragment knowledgeArticleFragment = new KnowledgeArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        bundle.putSerializable("list", (Serializable) children);
        knowledgeArticleFragment.setArguments(bundle);
        return knowledgeArticleFragment;
    }
}
