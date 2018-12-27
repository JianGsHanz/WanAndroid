package com.zyh.wanandroid.ui.collect.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.model.CollectResult;

import java.util.List;
import java.util.Random;

/**
 * author : zyh
 * Date : 2018/12/27
 * Description :
 */
public class CollectAdapter extends BaseMultiItemQuickAdapter<CollectResult.DataBean.DatasBean,CollectAdapter.ViewHolder>{

    private Random random;
    private int[] colorRess = {
            R.drawable.shape_head_red,
            R.drawable.shape_head_blue,
            R.drawable.shape_head_orange,
            R.drawable.shape_head_green};

    public CollectAdapter(List<CollectResult.DataBean.DatasBean> data) {
        super(data);
        addItemType(CollectResult.DataBean.DatasBean.FIRST_TYPE, R.layout.item_collect_rv_one);
        addItemType(CollectResult.DataBean.DatasBean.SECOND_TYPE, R.layout.item_collect_rv_two);
    }

    @Override
    protected void convert(ViewHolder helper, CollectResult.DataBean.DatasBean item) {
        switch (helper.getItemViewType()){
            case CollectResult.DataBean.DatasBean.FIRST_TYPE:
                type(helper,item,CollectResult.DataBean.DatasBean.FIRST_TYPE);
                break;
            case CollectResult.DataBean.DatasBean.SECOND_TYPE:
                type(helper,item,CollectResult.DataBean.DatasBean.SECOND_TYPE);
                break;
        }
    }

    private void type(ViewHolder helper, CollectResult.DataBean.DatasBean item,int type) {
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_head_color, item.getAuthor().substring(0,1))
                    .setBackgroundRes(R.id.tv_head_color, getRandomColor())
                    .setText(R.id.tv_head_name, item.getAuthor());
        }
        helper.setText(R.id.tv_head_type,item.getChapterName())
                .setText(R.id.tv_title_content,item.getTitle())
                .setText(R.id.tv_title_desc,TextUtils.isEmpty(item.getDesc()) ? item.getLink() : item.getDesc())
                .setText(R.id.tv_time,item.getNiceDate());
        ImageView iv_heart = (ImageView)helper.getView(R.id.iv_heart);
        iv_heart.setColorFilter(Color.RED);

        if (type == CollectResult.DataBean.DatasBean.SECOND_TYPE){
            ((SimpleDraweeView)helper.getView(R.id.iv_pic_collect))
                    .setImageURI(item.getEnvelopePic());
        }
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
