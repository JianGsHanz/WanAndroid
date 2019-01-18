package com.zyh.wanandroid.ui.category.list;

import android.text.TextUtils;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;
import com.zyh.wanandroid.model.CategoryListResult;
import com.zyh.wanandroid.net.AppApis;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Time:2018/12/19
 * Author:ZYH
 * Description:
 */
public class CategoryListFPresenter extends AbsBasePresenter<CategoryListConstract.view> implements
        CategoryListConstract.presenter {

    private AppApis appApis;
    private int page = 1;
    private boolean isRefresh = false;
    private int id;
    private String keyWord;

    @Inject
    public CategoryListFPresenter(AppApis appApis) {
        this.appApis = appApis;
    }

    @Override
    public void autoRefresh(int id,String keyWord) {
        this.id = id;
        this.keyWord = keyWord;
        page = 1;
        isRefresh = true;
        if (TextUtils.isEmpty(keyWord))
            getCategoryList();
        else
            getSearchList();
    }

    @Override
    public void loadMore() {
        page++;
        isRefresh = false;
        if (TextUtils.isEmpty(keyWord))
            getCategoryList();
        else
            getSearchList();
    }

    private void getCategoryList(){
        registerRx(appApis.getCategoryList(page,id)
                .compose(RxUtils.<CategoryListResult>rxSchedulerHelpe())
                .subscribe(new Consumer<CategoryListResult>() {
                    @Override
                    public void accept(CategoryListResult categoryListResult) throws Exception {
                        if (categoryListResult.getData().getDatas().size() != 0)
                            mView.getCategoryListSuccess(categoryListResult.getData(),isRefresh);
                        else
                            mView.getCategoryListFail(categoryListResult.getErrorMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.getCategoryListFail(throwable.getMessage());
                    }
                }));
    }

    private void getSearchList() {
        registerRx(appApis.getSearchList(page,keyWord)
                .compose(RxUtils.<CategoryListResult>rxSchedulerHelpe())
                .subscribe(new Consumer<CategoryListResult>() {
                    @Override
                    public void accept(CategoryListResult categoryListResult) throws Exception {
                        if (categoryListResult.getData().getDatas().size() != 0)
                            mView.getCategoryListSuccess(categoryListResult.getData(),isRefresh);
                        else
                            mView.getCategoryListFail("暂无数据");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.getCategoryListFail(throwable.getMessage());
                    }
                }));
    }

}
