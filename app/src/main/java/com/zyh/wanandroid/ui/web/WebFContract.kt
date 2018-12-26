package com.zyh.wanandroid.ui.web

import com.common.base.BasePresenter
import com.common.base.BaseView

interface WebFContract{
    interface view : BaseView {
        fun onCollectSuccess()
    }
    interface presenter : BasePresenter {
        fun articleCollect(id : Int)
    }
}