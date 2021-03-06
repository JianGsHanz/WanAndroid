package com.zyh.wanandroid.ui.home

import com.common.base.BasePresenter
import com.common.base.BaseView
import com.zyh.wanandroid.model.BannerResult
import com.zyh.wanandroid.model.HomeResult

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
interface HomeFConstract{
    interface view : BaseView {
        fun getBannerSuccess(listBaseResult : List<BannerResult>)
        fun getHomeListSuccess(homeDatasResult : HomeResult,isRefresh : Boolean)
        fun getHomeListFail(error : String)
    }
    interface presenter : BasePresenter{
        fun autoRefresh()
        fun loadMore()
    }
}