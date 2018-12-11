package com.zyh.wanandroid.ui.mine;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.base.BaseMvpFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.utils.view.CustomSettingLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
public class MineFragment extends BaseMvpFragment<MineFPresenter> implements MineFConstract.view {

    Unbinder unbinder;
    @BindView(R.id.iv_head_round)
    SimpleDraweeView ivHeadRound;
    @BindView(R.id.bt_login_register)
    android.widget.Button btLoginRegister;
    @BindView(R.id.tv_collect)
    CustomSettingLayout tvCollect;
    @BindView(R.id.tv_knowledge)
    CustomSettingLayout tvKnowledge;
    @BindView(R.id.tv_todo)
    CustomSettingLayout tvTodo;
    @BindView(R.id.tv_update)
    CustomSettingLayout tvUpdate;
    @BindView(R.id.tv_about)
    CustomSettingLayout tvAbout;

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
        ivHeadRound.setImageURI(Uri.parse("res://"+App.getInstance().getPackageName()+"/"+R.mipmap.ic_head_default));
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
    @butterknife.OnClick({R.id.bt_login_register,R.id.tv_collect,R.id.tv_knowledge,R.id.tv_todo,R.id.tv_update,R.id.tv_about})
    public void onViewClicked(View view) {
         switch (view.getId()){
             case R.id.bt_login_register:
//                 start();
                 break;
             case R.id.tv_collect: break;
             case R.id.tv_knowledge: break;
             case R.id.tv_todo: break;
             case R.id.tv_update: break;
             case R.id.tv_about: break;
         }
    }
}
