package com.zyh.wanandroid.ui.web;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;
import com.zyh.wanandroid.model.BaseResult;
import com.zyh.wanandroid.net.AppApis;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class WebFPresenter extends AbsBasePresenter<WebFContract.view> implements WebFContract.presenter {

    private AppApis appApis;

    @Inject
    public WebFPresenter(AppApis appApis){
        this.appApis = appApis;
    }

    @Override
    public void articleCollect(int id) {
        registerRx(appApis.collect(id)
        .compose(RxUtils.<BaseResult<String>>rxSchedulerHelpe())
        .subscribe(new Consumer<BaseResult<String>>() {
            @Override
            public void accept(BaseResult<String> result) throws Exception {
                if (result.getErrorCode() == 0)
                    mView.onCollectSuccess();
            }
        }));
    }

    @Override
    public void unArticleCollect(int id) {
        registerRx(appApis.unCollect(id)
        .compose(RxUtils.<BaseResult<String>>rxSchedulerHelpe())
        .subscribe(new Consumer<BaseResult<String>>() {
            @Override
            public void accept(BaseResult<String> result) throws Exception {
                if (result.getErrorCode() == 0)
                    mView.unCollectSuccess();
                if (result.getErrorCode() == -1001)
                    mView.unOverdue();
            }
        }));
    }

    @Override
    public void unCollectPage(int id, int originId) {
        registerRx(appApis.unCollectPage(id,originId)
                .compose(RxUtils.<BaseResult<String>>rxSchedulerHelpe())
                .subscribe(new Consumer<BaseResult<String>>() {
                    @Override
                    public void accept(BaseResult<String> result) throws Exception {
                        if (result.getErrorCode() == 0)
                            mView.unCollectSuccess();
                    }
                }));
    }
}
