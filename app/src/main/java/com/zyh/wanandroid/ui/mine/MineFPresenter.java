package com.zyh.wanandroid.ui.mine;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;
import com.zyh.wanandroid.model.BaseResult;
import com.zyh.wanandroid.net.AppApis;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * author : zyh
 * Date : 2019/1/30
 * Description :
 */
public class MineFPresenter extends AbsBasePresenter<MineFConstract.view> implements MineFConstract.presenter{
    private AppApis appApis;

    @Inject
    public MineFPresenter(AppApis appApis){
        this.appApis = appApis;
    }

    @Override
    public void logout() {
        registerRx(appApis.logout()
        .compose(RxUtils.<BaseResult<String>>rxSchedulerHelpe())
        .subscribe(new Consumer<BaseResult<String>>() {
            @Override
            public void accept(BaseResult<String> baseResult) throws Exception {
                if (baseResult.getErrorCode() == 0)
                    mView.logoutSuccess();
                else
                    mView.logoutFail(baseResult.getErrorMsg());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.logoutFail(throwable.getMessage());
            }
        }));
    }
}
