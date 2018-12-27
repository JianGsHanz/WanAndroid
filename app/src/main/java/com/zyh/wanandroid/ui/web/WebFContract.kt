package com.zyh.wanandroid.ui.web

import com.common.base.BasePresenter
import com.common.base.BaseView

interface WebFContract{
    interface view : BaseView {
        fun onCollectSuccess()
        fun unCollectSuccess()
        fun unOverdue()
    }
    interface presenter : BasePresenter {
        fun articleCollect(id : Int)
        fun unArticleCollect(id : Int)
        fun unCollectPage(id: Int,originId :Int)
    }
}