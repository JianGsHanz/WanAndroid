package com.zyh.wanandroid.ui.navigation;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;
import com.zyh.wanandroid.model.BaseResult;
import com.zyh.wanandroid.model.NavigationResult;
import com.zyh.wanandroid.net.AppApis;
import com.zyh.wanandroid.utils.CustomConsumer;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
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
        appApis.getNavigation()
                .compose(RxUtils.<NavigationResult>rxSchedulerHelpe())
                .subscribe(new CustomConsumer<NavigationResult>(((NavigationFragment)mView).getActivity()) {
                    @Override
                    public void onDisposable(@NotNull Disposable d) {
                        registerRx(d);
                    }

                    @Override
                    public void accept(NavigationResult navigationResult) {
                        if (navigationResult.getErrorCode() == 0)
                            mView.getNavigationSuccess(navigationResult);
                    }
                });
    }

}
