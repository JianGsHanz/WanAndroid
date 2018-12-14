package com.zyh.wanandroid.ui.mine

import com.common.base.BasePresenter
import com.common.base.BaseView

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
interface MineFConstract{
    interface view : BaseView {
        fun logoutSuccess()
        fun logoutFail(errorMsg: String)
    }
    interface presenter : BasePresenter{
        fun logout()
    }
}