package com.zyh.wanandroid.ui.login;

import android.app.Activity;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;
import com.zyh.wanandroid.model.BaseResult;
import com.zyh.wanandroid.model.UserResult;
import com.zyh.wanandroid.net.AppApis;
import com.zyh.wanandroid.utils.CustomConsumer;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.multibindings.ElementsIntoSet;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LoginRegisterFPresenter extends AbsBasePresenter<LoginRegisterFConstract.view> implements LoginRegisterFConstract.presenter{
    private AppApis appApis;

    @Inject
    public LoginRegisterFPresenter(AppApis appApis){
        this.appApis = appApis;
    }

    @Override
    public void requestLogin(@NotNull String userName, @NotNull String password) {
        /*registerRx(appApis.login(userName,password)
        .compose(RxUtils.<BaseResult<UserResult>>rxSchedulerHelpe())
        .subscribe(new Consumer<BaseResult<UserResult>>() {
            @Override
            public void accept(BaseResult<UserResult> baseResult) throws Exception {
                UserResult userResult = baseResult.getData();
                if (baseResult.getErrorCode() == 0&&baseResult.getErrorMsg().isEmpty())
                    mView.loginRegisterSuccess(userResult);
                else
                    mView.loginRegisterFail(baseResult.getErrorMsg());

            }
        }));*/
        appApis.login(userName,password)
                .compose(RxUtils.<BaseResult<UserResult>>rxSchedulerHelpe())
                .subscribe(new CustomConsumer<BaseResult<UserResult>>(((LoginRegisterFragment)mView).getActivity()) {
                    @Override
                    public void onDisposable(@NotNull Disposable d) {
                        registerRx(d);
                    }

                    @Override
                    public void accept(BaseResult<UserResult> baseResult) {
                        UserResult userResult = baseResult.getData();
                        if (baseResult.getErrorCode() == 0&&baseResult.getErrorMsg().isEmpty())
                            mView.loginRegisterSuccess(userResult);
                        else
                            mView.loginRegisterFail(baseResult.getErrorMsg());
                    }
                });
    }

    @Override
    public void requestRegister(@NotNull String username, @NotNull String password, @NotNull String repassword) {
        registerRx(appApis.register(username,password,repassword)
        .compose(RxUtils.<BaseResult<UserResult>>rxSchedulerHelpe())
        .subscribe(new Consumer<BaseResult<UserResult>>() {
            @Override
            public void accept(BaseResult<UserResult> baseResult) throws Exception {
                UserResult userResult = baseResult.getData();
                if (baseResult.getErrorCode() == 0&&baseResult.getErrorMsg().isEmpty())
                    mView.loginRegisterSuccess(userResult);
                else
                    mView.loginRegisterFail(baseResult.getErrorMsg());
            }
        }));
    }
}
