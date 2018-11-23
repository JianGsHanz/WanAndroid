package com.common.base;



public interface IBaseMvpActivity<T extends AbsBasePresenter> extends IBaseActivity {

    T getPresenter();

}
