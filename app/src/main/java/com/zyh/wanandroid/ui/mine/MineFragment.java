package com.zyh.wanandroid.ui.mine;

import com.common.base.BaseMvpFragment;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;

import javax.inject.Inject;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
public class MineFragment extends BaseMvpFragment<MineFPresenter> implements  MineFConstract.view{

    @Inject
    public MineFragment(){}
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

    }
}
