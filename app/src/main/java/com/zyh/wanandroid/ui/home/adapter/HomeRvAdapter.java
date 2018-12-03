package com.zyh.wanandroid.ui.home.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.HomeResult;

import java.util.List;
import java.util.Random;

/**
 * author : zyh
 * Date : 2018/12/3
 * Description :
 */
public class HomeRvAdapter extends BaseQuickAdapter<HomeResult.DatasBean,HomeRvAdapter.ViewHolder> {

    private Random random;
    private int[] colorRess = {
            R.drawable.shape_head_red,
            R.drawable.shape_head_blue,
            R.drawable.shape_head_orange,
            R.drawable.shape_head_green};
    public HomeRvAdapter(int layoutResId, @Nullable List<HomeResult.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ViewHolder helper, HomeResult.DatasBean item) {
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_head_color, item.getAuthor().substring(0,1));
            helper.setBackgroundRes(R.id.tv_head_color, getRandomColor());
            helper.setText(R.id.tv_head_name, item.getAuthor());
        }
        helper.setText(R.id.tv_head_type,getChapterName(item));
        helper.setText(R.id.tv_title_content,item.getTitle());
        helper.setText(R.id.tv_title_desc,TextUtils.isEmpty(item.getDesc()) ? item.getLink() : item.getDesc());
        helper.setText(R.id.tv_time,item.getNiceDate());
        ImageView iv_heart = (ImageView)helper.getView(R.id.iv_heart);
        if (item.isCollect())
            iv_heart.setColorFilter(R.color.colorRed);
        else
            iv_heart.setColorFilter(R.color.colorGray);
    }

    private String getChapterName(HomeResult.DatasBean item){
        if (item.getSuperChapterName().isEmpty())
            return item.getChapterName();
        else if (item.getChapterName().isEmpty())
            return item.getSuperChapterName();
        else
            return item.getChapterName()+"/"+item.getSuperChapterName();
    }
    private int getRandomColor(){
        if (random == null)
        random = new Random();
        return colorRess[random.nextInt(4)];
    }

    class ViewHolder extends BaseViewHolder{
        public ViewHolder(View view) {
            super(view);
        }
    }
}
