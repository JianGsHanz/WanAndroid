package com.zyh.wanandroid.ui.category.list

import com.common.base.BasePresenter
import com.common.base.BaseView
import com.zyh.wanandroid.model.CategoryListResult

/**
 *Time:2018/12/19
 *Author:ZYH
 *Description:
 */
interface CategoryListConstract{
    interface view : BaseView{
        fun getCategoryListSuccess(dataResult: CategoryListResult.DataBean,isRefresh : Boolean)
        fun getCategoryListFail(errorMsg : String)
    }
    interface presenter : BasePresenter{
        fun autoRefresh(id : Int)
        fun loadMore()
    }
}