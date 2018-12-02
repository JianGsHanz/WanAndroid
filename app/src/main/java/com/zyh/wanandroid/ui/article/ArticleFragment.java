package com.zyh.wanandroid.ui.article;

import com.common.base.BaseMvpFragment;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;

import javax.inject.Inject;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
public class ArticleFragment extends BaseMvpFragment<ArticleFPresenter> implements  ArticleFConstract.view{

    @Inject
    public ArticleFragment(){}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {

    }
}
