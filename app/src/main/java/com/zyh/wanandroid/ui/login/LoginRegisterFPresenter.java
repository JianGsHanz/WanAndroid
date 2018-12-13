package com.zyh.wanandroid.ui.login;

import com.common.base.AbsBasePresenter;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class LoginRegisterFPresenter extends AbsBasePresenter<LoginRegisterFConstract.view> implements LoginRegisterFConstract.presenter{
    @Inject
    public LoginRegisterFPresenter(){}

    @Override
    public void releaseData() {

    }

    @Override
    public void requestLogin(@NotNull String userName, @NotNull String password) {

    }

    @Override
    public void requestRegister(@NotNull String username, @NotNull String password, @NotNull String repassword) {

    }
}
