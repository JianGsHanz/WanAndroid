package com.zyh.wanandroid.ui.category.list;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;
import com.zyh.wanandroid.model.CategoryListResult;
import com.zyh.wanandroid.net.AppApis;
import com.zyh.wanandroid.utils.CustomConsumer;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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

    @Inject
    public CategoryListFPresenter(AppApis appApis) {
        this.appApis = appApis;
    }

    @Override
    public void autoRefresh(int id) {
        this.id = id;
        page = 1;
        isRefresh = true;
        getCategoryList();
    }

    @Override
    public void loadMore() {
        page++;
        isRefresh = false;
        getCategoryList();
    }

    private void getCategoryList(){
        appApis.getCategoryList(page,id)
                .compose(RxUtils.<CategoryListResult>rxSchedulerHelpe())
                .subscribe(new CustomConsumer<CategoryListResult>(((CategoryListFragment)mView).getActivity()) {
                    @Override
                    public void onDisposable(@NotNull Disposable d) {
                        registerRx(d);
                    }

                    @Override
                    public void accept(CategoryListResult categoryListResult) {
                        if (categoryListResult.getData().getDatas().size() != 0)
                            mView.getCategoryListSuccess(categoryListResult.getData(),isRefresh);
                        else
                            mView.getCategoryListFail(categoryListResult.getErrorMsg());
                    }
                });
    }


}
