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
        ivHeadRound.setImageURI(Uri.parse("res:///"+R.mipmap.ic_launcher));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
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
