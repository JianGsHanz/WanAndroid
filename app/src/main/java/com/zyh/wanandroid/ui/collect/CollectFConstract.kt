package com.zyh.wanandroid.ui.collect

import com.common.base.BasePresenter
import com.common.base.BaseView
import com.zyh.wanandroid.model.CollectResult

/**
 * author : zyh
 * Date : 2018/12/27
 * Description :
 */
interface CollectFConstract{
    interface view : BaseView{
        fun getCollectListSuccess(data: CollectResult.DataBean,isRefresh : Boolean)
        fun getCollectListFail(error: String)
        fun isEmptyLayout()
        fun unOverdue()
    }
    interface presenter : BasePresenter{
        fun onRefresh()
        fun onLoadMore()
    }
}