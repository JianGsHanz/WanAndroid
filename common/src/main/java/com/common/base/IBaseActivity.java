package com.common.base;



public interface IBaseActivity {

    /**
     * 获取布局id
     * @return
     */
    int getLayoutId();

    /**
     * 初始化dagger注入
     */
    void initInject();

    void initViewAndEvent();

}
