package com.zyh.wanandroid.ui.home

import android.content.Context
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
    }
    interface presenter : BasePresenter{
        fun autoRefresh(context: Context)
        fun loadMore(context: Context)
    }
}