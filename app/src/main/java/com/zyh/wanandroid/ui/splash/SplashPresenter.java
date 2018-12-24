package com.zyh.wanandroid.ui.splash;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * author : zyh
 * Date : 2018/11/29
 * Description :
 */
public class SplashPresenter extends AbsBasePresenter<SplashContract.view> implements SplashContract.presenter {

    @Inject
    public SplashPresenter(){}

    @Override
    public void loadData() {
        registerRx(Observable.interval(3, 1, TimeUnit.SECONDS)
                .compose(((SplashActivity) mView).<Long>bindToLifecycle())
                .compose(RxUtils.<Long>rxSchedulerHelpe())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong == 0)
                            mView.result();
                    }
                }));
    }

}
