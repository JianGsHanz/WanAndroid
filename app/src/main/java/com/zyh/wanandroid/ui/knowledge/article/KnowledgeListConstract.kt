package com.zyh.wanandroid.ui.knowledge.article

import com.common.base.BasePresenter
import com.common.base.BaseView
import com.zyh.wanandroid.model.KnowledgeListResult

interface KnowledgeListConstract{
    interface view : BaseView{
        fun getKnowledgeListSuccess(dataResult : KnowledgeListResult.DataBean , isRefresh : Boolean)
    }
    interface presenter : BasePresenter{
        fun autoRefresh(id : Int)
        fun loadMore()
    }
}