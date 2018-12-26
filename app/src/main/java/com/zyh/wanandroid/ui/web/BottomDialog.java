package com.zyh.wanandroid.ui.web;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyh.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Time:2018/12/24
 * Author:ZYH
 * Description:
 */
public class BottomDialog extends BottomSheetDialogFragment {


    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_browser)
    TextView tvBrowser;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    Unbinder unbinder;
    private boolean collect;
    private OnDialogClickListener onClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        collect = getArguments().getBoolean("collect", false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        collectUrl(collect);
    }

    @OnClick({R.id.tv_share, R.id.tv_collect, R.id.tv_copy, R.id.tv_browser, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_share: //分享
                onClickListener.onShare();
                break;
            case R.id.tv_collect: //收藏
                onClickListener.onCollect();
                break;
            case R.id.tv_copy: //复制链接
                onClickListener.onCopy();
                break;
            case R.id.tv_browser: //打开浏览器
                onClickListener.onBrowser();
                break;
            case R.id.tv_cancel:

                break;
        }
        dismiss();
    }

    public void collectUrl(boolean collect) {
        if (collect){
            tvCollect.setText("取消收藏");
        }else {
            tvCollect.setText("收藏");
        }
    }



    public static BottomDialog newInstance(boolean collect){
        BottomDialog bottomDialog = new BottomDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean("collect",collect);
        bottomDialog.setArguments(bundle);
        return bottomDialog;
    }

    interface OnDialogClickListener{
        void onShare();
        void onCollect();
        void onCopy();
        void onBrowser();
    }
    public void setOnDialogClick(OnDialogClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
