package com.zyh.wanandroid.ui.navigation

import com.common.base.BasePresenter
import com.common.base.BaseView
import com.zyh.wanandroid.model.NavigationResult

/**
 * author : zyh
 * Date : 2018/12/17
 * Description :
 */
interface NavigationConstract{
    interface view : BaseView{
        fun getNavigationSuccess(navigationResult: NavigationResult)
    }
    interface presenter : BasePresenter{}
}