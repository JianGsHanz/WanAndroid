package com.zyh.wanandroid.ui.mine;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.common.util.PrefsUtils;
import com.common.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.base.LBaseMvpFragment;
import com.zyh.wanandroid.ui.about.AboutFragment;
import com.zyh.wanandroid.ui.collect.CollectFragment;
import com.zyh.wanandroid.ui.knowledge.article.KnowledgeArticleFragment;
import com.zyh.wanandroid.ui.login.LoginRegisterFragment;
import com.zyh.wanandroid.ui.main.MainFragment;
import com.zyh.wanandroid.utils.view.CustomSettingLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :我的
 */
public class MineFragment extends LBaseMvpFragment<MineFPresenter> implements MineFConstract.view {

    Unbinder unbinder;
    @BindView(R.id.iv_head_round)
    SimpleDraweeView ivHeadRound;
    @BindView(R.id.bt_login_register)
    Button btLoginRegister;
    @BindView(R.id.tv_collect)
    CustomSettingLayout tvCollect;
    @BindView(R.id.tv_todo)
    CustomSettingLayout tvTodo;
    @BindView(R.id.tv_update)
    CustomSettingLayout tvUpdate;
    @BindView(R.id.tv_about)
    CustomSettingLayout tvAbout;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    private static final int REQUEST_CODE = 0;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @Inject
    CollectFragment collectFragment;
    @Inject
    AboutFragment aboutFragment;
    @Inject
    KnowledgeArticleFragment knowledgeArticleFragment;

    @Inject
    public MineFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {
        ivHeadRound.setImageURI(Uri.parse("res://" + App.getInstance().getPackageName() + "/" + R.mipmap.ic_head_default));
        isLogin();
    }

    @OnClick({R.id.bt_login_register, R.id.tv_collect,  R.id.tv_todo, R.id.tv_update, R.id.tv_about,R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login_register:
                ((MainFragment) getParentFragment()).goFragment(LoginRegisterFragment.newInstance(), -1);
                break;
            case R.id.tv_collect:
                String userName = PrefsUtils.getInstance().getString("userName", "");
                if (TextUtils.isEmpty(userName))
                    ((MainFragment) getParentFragment()).goFragment(LoginRegisterFragment.newInstance(), -1);
                else
                    ((MainFragment) getParentFragment()).goFragment(collectFragment,-1);
                break;
            case R.id.tv_todo:
                ToastUtils.showShortToast("未完待续...");
                break;
            case R.id.tv_update:
                ToastUtils.showShortToast("未完待续...");
                break;
            case R.id.tv_about:
                ((MainFragment) getParentFragment()).goFragment(aboutFragment,-1);
                break;
            case R.id.tv_logout:
                mPresenter.logout();
                break;
        }
    }

    @Override
    public void logoutSuccess() {
        PrefsUtils.getInstance().remove("userName");
        tvLogout.setVisibility(View.GONE);
        btLoginRegister.setVisibility(View.VISIBLE);
        tvUserName.setText("");
    }

    @Override
    public void logoutFail(String errorMsg) {
        ToastUtils.showShortToast(errorMsg);
    }

    private void isLogin() {
        String userName = PrefsUtils.getInstance().getString("userName", "");
        if (TextUtils.isEmpty(userName)){
            tvLogout.setVisibility(View.GONE);
            tvUserName.setText(userName);
            btLoginRegister.setVisibility(View.VISIBLE);
        }else {
            tvLogout.setVisibility(View.VISIBLE);
            tvUserName.setText(userName);
            btLoginRegister.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String o){
        if (o.equals("login")||o.equals("loginOut"))
        isLogin();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


}
