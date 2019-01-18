package com.common.base;

/**
 * Created by zyh on 17/3/23.
 */

public interface BaseView {
    void showNormal();
    void showLoading();
    void showEmpty();
    void showError(String error);
    void reLoad();
}
