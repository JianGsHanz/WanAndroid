package com.zyh.wanandroid.ui.collect;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;
import com.zyh.wanandroid.model.CollectResult;
import com.zyh.wanandroid.net.AppApis;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : zyh
 * Date : 2018/12/27
 * Description :
 */
public class CollectFPresenter extends AbsBasePresenter<CollectFConstract.view> implements CollectFConstract.presenter{
    private int page = 0;
    private boolean isRefresh = false;
    private AppApis appApis;

    @Inject
    public CollectFPresenter(AppApis appApis) {
        this.appApis = appApis;
    }

    @Override
    public void onRefresh() {
        page = 0;
        isRefresh = true;
        getCollectList();
    }

    @Override
    public void onLoadMore() {
        page++;
        isRefresh = false;
        getCollectList();
    }

    private void getCollectList(){
        registerRx(appApis.getCollectList(page)
        .compose(RxUtils.<CollectResult>rxSchedulerHelpe())
        .subscribe(new Consumer<CollectResult>() {
            @Override
            public void accept(CollectResult collectResult) throws Exception {
                if (collectResult.getErrorCode() == -1001)
                    mView.unOverdue();
                else if (!isRefresh && collectResult.getData().getDatas().size() == 0)
                    mView.getCollectListFail(collectResult.getErrorMsg());
                else if (collectResult.getData().getDatas().size() != 0)
                    mView.getCollectListSuccess(collectResult.getData(), isRefresh);
                else if (isRefresh && collectResult.getData().getDatas().size() == 0)
                    mView.isEmptyLayout();


            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }));
    }
}
