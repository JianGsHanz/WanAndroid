package com.zyh.wanandroid.ui.login;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.base.BaseMvpFragment;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginRegisterFragment extends BaseMvpFragment<LoginRegisterFPresenter> implements LoginRegisterFConstract.view {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    Unbinder unbinder;

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
                //todo 选择切换监听
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public static LoginRegisterFragment newInstance(){
        LoginRegisterFragment fragment = new LoginRegisterFragment();
        return fragment;
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
