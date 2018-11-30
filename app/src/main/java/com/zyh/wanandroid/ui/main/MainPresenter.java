package com.zyh.wanandroid.ui.main;

import com.common.base.AbsBasePresenter;

import javax.inject.Inject;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
public class MainPresenter extends AbsBasePresenter<MainContract.view> implements MainContract.presenter{
    @Inject
    public MainPresenter(){}

    @Override
    public void releaseData() {

    }
}
