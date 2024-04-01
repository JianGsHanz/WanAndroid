package com.zyh.wanandroid.ui.knowledge.adapter;

import androidx.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.ArticleResult;

import java.util.List;

/**
 * author : zyh
 * Date : 2018/12/18
 * Description :
 */
public class ArticleRvAdapter extends BaseQuickAdapter<ArticleResult.DataBean,ArticleRvAdapter.ViewHolder> {


    public ArticleRvAdapter(int layoutResId, @Nullable List<ArticleResult.DataBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(ViewHolder helper, ArticleResult.DataBean item) {
        helper.setText(R.id.tv_name,item.getName());
        StringBuffer sb = new StringBuffer();
        for (ArticleResult.DataBean.ChildrenBean items :item.getChildren()){
            sb.append(items.getName()).append("\t\t");
        }
        helper.setText(R.id.tv_children_name,sb.toString());
    }

    class ViewHolder extends BaseViewHolder{

        public ViewHolder(View view) {
            super(view);
        }
    }
}
