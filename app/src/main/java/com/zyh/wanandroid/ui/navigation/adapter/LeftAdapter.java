package com.zyh.wanandroid.ui.navigation.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.NavigationResult;

import java.util.List;

/**
 * author : zyh
 * Date : 2018/12/17
 * Description :
 */
public class LeftAdapter extends BaseQuickAdapter<NavigationResult.DataBean,LeftAdapter.ViewHolder>{

    private int beforePosition = 0;

    public LeftAdapter(int layoutResId, @Nullable List<NavigationResult.DataBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(final ViewHolder helper, NavigationResult.DataBean item) {
        helper.setText(R.id.left_tv,item.getName());

        if (helper.getAdapterPosition() == beforePosition) {
            helper.getView(R.id.left_tv).setBackgroundColor(R.color.colorWhite);
        }

    }

    public void setPosition(int beforePosition){
        this.beforePosition = beforePosition;
        notifyDataSetChanged();
    }
    class ViewHolder extends BaseViewHolder{

        public ViewHolder(View view) {
            super(view);
        }
    }
}
