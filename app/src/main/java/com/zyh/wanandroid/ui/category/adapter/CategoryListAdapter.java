package com.zyh.wanandroid.ui.category.adapter;

import androidx.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.CategoryListResult;

import java.util.List;

/**
 * author : zyh
 * Date : 2018/12/20
 * Description :
 */
public class CategoryListAdapter extends BaseQuickAdapter<CategoryListResult.DataBean.DatasBean,CategoryListAdapter.ViewHolder>{

    public CategoryListAdapter(int layoutResId, @Nullable List<CategoryListResult.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ViewHolder helper, CategoryListResult.DataBean.DatasBean item) {
        ((SimpleDraweeView)helper.getView(R.id.iv_pic_category)).setImageURI(item.getEnvelopePic());
        helper.setText(R.id.tv_title_category,item.getTitle());
        helper.setText(R.id.tv_desc_category,item.getDesc());
        helper.setText(R.id.tv_author_category,item.getAuthor());
        helper.setText(R.id.tv_date_category,item.getNiceDate());
    }

    class ViewHolder extends BaseViewHolder{
        public ViewHolder(View view) {
            super(view);
        }
    }
}
