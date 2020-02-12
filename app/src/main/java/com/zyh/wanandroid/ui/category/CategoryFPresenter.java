package com.zyh.wanandroid.ui.category;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;
import com.zyh.wanandroid.model.CategoryResult;
import com.zyh.wanandroid.net.AppApis;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : zyh
 * Date : 2019/1/30
 * Description :
 */
public class CategoryFPresenter extends AbsBasePresenter<CategoryFConstract.view> implements CategoryFConstract.presenter{
    private AppApis appApis;

    @Inject
    public CategoryFPresenter(AppApis appApis){
        this.appApis = appApis;
    }

    @Override
    public void loadData() {
        registerRx(appApis.getCategory()
        .compose(RxUtils.<CategoryResult>rxSchedulerHelpe())
        .subscribe(new Consumer<CategoryResult>() {
            @Override
            public void accept(CategoryResult categoryResult) throws Exception {
                if (categoryResult.getErrorCode() == 0)
                    mView.getCategorySuccess(categoryResult.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }));

    }

}
