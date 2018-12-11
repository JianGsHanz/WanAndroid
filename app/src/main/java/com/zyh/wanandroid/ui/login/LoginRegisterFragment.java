package com.zyh.wanandroid.ui.login;

import com.common.base.BaseMvpFragment;
import com.zyh.wanandroid.R;

import javax.inject.Inject;

public class LoginRegisterFragment extends BaseMvpFragment<LoginRegisterFPresenter> implements LoginRegisterFConstract.view{

    @Inject
    public LoginRegisterFragment(){}
    @Override
    protected int getLayoutId() {
        return R.layout.fragmment_login_register;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initViewAndEvent() {

    }
}
