package com.zyh.wanandroid.ui.knowledge.list;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;
import com.zyh.wanandroid.model.KnowledgeListResult;
import com.zyh.wanandroid.net.AppApis;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class KnowledgeListFPresenter extends AbsBasePresenter<KnowledgeListConstract.view> implements KnowledgeListConstract.presenter{
    private AppApis appApis;
    private int page = 0;
    private int id;
    private boolean isRefresh = false;

    @Inject
    public KnowledgeListFPresenter(AppApis appApis) {
        this.appApis = appApis;
    }

    @Override
    public void autoRefresh(int id) {
        this.id = id;
        isRefresh = true;
        page = 0;
        getKnowledgeList();
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        page++;
        getKnowledgeList();
    }

    public void getKnowledgeList(){
        registerRx(appApis.getKnowledgeList(page,id)
                .compose(RxUtils.<KnowledgeListResult>rxSchedulerHelpe())
                .subscribe(new Consumer<KnowledgeListResult>() {
                    @Override
                    public void accept(KnowledgeListResult dataResult) throws Exception {
                        if (dataResult!=null &&dataResult.getData().getDatas().size() != 0)
                            mView.getKnowledgeListSuccess(dataResult.getData(),isRefresh);
                        else
                            mView.getKnowledgeListFail(dataResult.getErrorMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.getKnowledgeListFail(throwable.getMessage());
                    }
                }));
    }

}
