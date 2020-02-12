package com.zyh.wanandroid.ui.category

import com.common.base.BasePresenter
import com.common.base.BaseView
import com.zyh.wanandroid.model.CategoryResult

/**
 * author : zyh
 * Date : 2019/1/30
 * Description :
 */
interface CategoryFConstract{
    interface view : BaseView {
        fun getCategorySuccess(dataResult: List<CategoryResult.DataBean>)
    }
    interface presenter : BasePresenter{}
}