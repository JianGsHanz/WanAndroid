package com.zyh.wanandroid.ui.knowledge;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;
import com.zyh.wanandroid.model.ArticleResult;
import com.zyh.wanandroid.net.AppApis;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
public class knowledgeFPresenter extends AbsBasePresenter<knowledgeFConstract.view> implements knowledgeFConstract.presenter{
    private AppApis appApis;

    @Inject
    public knowledgeFPresenter(AppApis appApis){
        this.appApis = appApis;
    }

    @Override
    public void loadData() {
        registerRx(appApis.getArticle()
        .compose(RxUtils.<ArticleResult>rxSchedulerHelpe())
        .subscribe(new Consumer<ArticleResult>() {
            @Override
            public void accept(ArticleResult articleResult) throws Exception {
                if (articleResult.getErrorCode() == 0) {
                    mView.getAricleSuccess(articleResult.getData());
                }
            }
        }));
    }

    @Override
    public void releaseData() {

    }
}
