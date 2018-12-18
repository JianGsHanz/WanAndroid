package com.zyh.wanandroid.ui.knowledge.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.KnowledgeListResult;

import java.util.List;

/**
 * Time:2018/12/18
 * Author:ZYH
 * Description:
 */
public class KnowledgeListAdapter extends BaseQuickAdapter<KnowledgeListResult.DataBean.DatasBean,KnowledgeListAdapter.ViewHolder>{

    public KnowledgeListAdapter(int layoutResId, @Nullable List<KnowledgeListResult.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ViewHolder helper, KnowledgeListResult.DataBean.DatasBean item) {
        helper.setText(R.id.tv_title_knowledge,item.getTitle());
        helper.setText(R.id.tv_author_knowledge,item.getAuthor());
        helper.setText(R.id.tv_date_knowledge,item.getNiceDate());
        helper.setText(R.id.tv_chapter_knowledge,
                item.getSuperChapterName()+" / "+ item.getChapterName());
    }

    class ViewHolder extends BaseViewHolder{
        public ViewHolder(View view) {
            super(view);
        }
    }
}
