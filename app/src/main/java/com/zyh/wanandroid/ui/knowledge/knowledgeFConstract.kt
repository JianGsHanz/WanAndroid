package com.zyh.wanandroid.ui.knowledge

import com.common.base.BasePresenter
import com.common.base.BaseView
import com.zyh.wanandroid.model.ArticleResult

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
interface knowledgeFConstract{
    interface view : BaseView {
        fun getAricleSuccess(data: List<ArticleResult.DataBean>)
    }
    interface presenter : BasePresenter{}
}