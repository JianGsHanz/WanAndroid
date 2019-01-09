package com.zyh.wanandroid.ui.login;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.common.base.BaseMvpFragment;
import com.common.util.PrefsUtils;
import com.common.util.ToastUtils;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.UserResult;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/**
 * author : zyh
 * Date : 2018/12/3
 * Description :登录注册
 */
public class LoginRegisterFragment extends BaseMvpFragment<LoginRegisterFPresenter> implements LoginRegisterFConstract.view {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    Unbinder unbinder;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.et_user_re_pwd)
    EditText etUserRePwd;
    @BindView(R.id.bt_login_register)
    Button btLoginRegister;
    @BindView(R.id.cl_parent)
    ConstraintLayout clParent;
    private int flag = 0;

    @Inject
    public LoginRegisterFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmment_login_register;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        tabLayout.addTab(tabLayout.newTab().setText("登录"));
        tabLayout.addTab(tabLayout.newTab().setText("注册"));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                clParent.setFocusable(true);
                clParent.setFocusableInTouchMode(true);
                clParent.requestFocus();
                etUserName.setText("");
                etUserPwd.setText("");
                etUserRePwd.setText("");
                if (tab.getText().equals(getString(R.string.string_login))) {
                    flag = 0;
                    if (etUserRePwd.getVisibility() == View.VISIBLE)
                        etUserRePwd.setVisibility(View.GONE);
                    if (btLoginRegister.getText().equals(getString(R.string.string_register)))
                        btLoginRegister.setText(R.string.string_login);
                } else {
                    flag = 1;
                    if (etUserRePwd.getVisibility() == View.GONE)
                        etUserRePwd.setVisibility(View.VISIBLE);
                    if (btLoginRegister.getText().equals(getString(R.string.string_login)))
                        btLoginRegister.setText(R.string.string_register);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public static LoginRegisterFragment newInstance() {
        LoginRegisterFragment fragment = new LoginRegisterFragment();
        return fragment;
    }

    @OnClick(R.id.bt_login_register)
    public void onViewClicked() {
        if (judgeLoginOrRegister())
            if (flag == 0)
                mPresenter.requestLogin(etUserName.getText().toString().trim(),
                        etUserPwd.getText().toString().trim());
            else
                mPresenter.requestRegister(etUserName.getText().toString().trim(),
                        etUserPwd.getText().toString().trim(),
                        etUserRePwd.getText().toString().trim());


    }

    private boolean judgeLoginOrRegister() {
        if (TextUtils.isEmpty(etUserName.getText().toString())) {
            ToastUtils.showShortToast("请输入用户名");
            return false;
        } else if (TextUtils.isEmpty(etUserPwd.getText().toString())) {
            ToastUtils.showShortToast("请输入密码");
            return false;
        } else if (etUserRePwd.getVisibility() == View.VISIBLE &&
                TextUtils.isEmpty(etUserRePwd.getText().toString())) {
            ToastUtils.showShortToast("请输入确认密码");
            return false;
        } else if (etUserRePwd.getVisibility() == View.VISIBLE &&
                (!etUserPwd.getText().toString().equals(etUserRePwd.getText().toString()))) {
            ToastUtils.showShortToast("两次密码输入不一致,请重新输入");
            etUserPwd.setText("");
            etUserRePwd.setText("");
            etUserPwd.requestFocus();
            return false;
        }
        return true;
    }


    @Override
    public void loginRegisterSuccess(@NotNull UserResult userResult) {
        PrefsUtils.getInstance().putString("userName", userResult.getUsername());
        EventBus.getDefault().post("login");
        _mActivity.onBackPressed();
    }

    @Override
    public void loginRegisterFail(@NotNull String errorMsg) {
        ToastUtils.showShortToast(errorMsg);
        etUserPwd.setText("");
        etUserRePwd.setText("");
        etUserName.requestFocus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
