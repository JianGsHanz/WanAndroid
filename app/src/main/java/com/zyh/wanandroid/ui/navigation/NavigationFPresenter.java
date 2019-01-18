package com.zyh.wanandroid.ui.navigation;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;
import com.zyh.wanandroid.model.NavigationResult;
import com.zyh.wanandroid.net.AppApis;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : zyh
 * Date : 2018/12/17
 * Description :
 */
public class NavigationFPresenter extends AbsBasePresenter<NavigationConstract.view> implements NavigationConstract.presenter{
    private AppApis appApis;

    @Inject
    public NavigationFPresenter(AppApis appApis) {
        this.appApis = appApis;
    }

    @Override
    public void loadData() {
        registerRx(appApis.getNavigation()
                .compose(RxUtils.<NavigationResult>rxSchedulerHelpe())
                .subscribe(new Consumer<NavigationResult>() {
                    @Override
                    public void accept(NavigationResult navigationResult) throws Exception {
                        if (navigationResult.getErrorCode() == 0)
                            mView.getNavigationSuccess(navigationResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

}
