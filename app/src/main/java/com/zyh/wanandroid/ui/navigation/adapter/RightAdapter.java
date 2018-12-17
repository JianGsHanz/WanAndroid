package com.zyh.wanandroid.ui.navigation.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.NavigationResult;
import com.zyh.wanandroid.utils.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class RightAdapter extends BaseQuickAdapter<NavigationResult.DataBean, RightAdapter.ViewHolder> {

    private FlowLayout right_fl;
    private ArrayList<String> list = new ArrayList<>();
    private int postion = 0;

    public RightAdapter(int layoutResId, @Nullable List<NavigationResult.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ViewHolder helper, NavigationResult.DataBean item) {
        List<NavigationResult.DataBean.ArticlesBean> articles = item.getArticles();
        list.clear();
        for (int i = 0; i < articles.size(); i++) {
            list.add(articles.get(i).getTitle());
        }
        right_fl.addViews(list, new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(String content) {

            }
        });

    }

    public void setPostion(int postion){
        this.postion = postion;
        notifyDataSetChanged();
    }
    class ViewHolder extends BaseViewHolder {

        public ViewHolder(View view) {
            super(view);
            right_fl = view.findViewById(R.id.right_fl);
        }

    }
}
