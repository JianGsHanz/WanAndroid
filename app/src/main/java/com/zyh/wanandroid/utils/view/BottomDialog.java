package com.zyh.wanandroid.utils.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.util.ToastUtils;
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
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @OnClick({R.id.tv_share, R.id.tv_collect, R.id.tv_copy, R.id.tv_browser, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_share:
                ToastUtils.showShortToast("分享");
                break;
            case R.id.tv_collect:
                ToastUtils.showShortToast("收藏");
                break;
            case R.id.tv_copy:
                ToastUtils.showShortToast("复制链接");
                break;
            case R.id.tv_browser:
                ToastUtils.showShortToast("打开浏览器");
                break;
            case R.id.tv_cancel:
                ToastUtils.showShortToast("取消");
                break;
        }
    }
    public static BottomDialog newInstance(){
        BottomDialog bottomDialog = new BottomDialog();
        return bottomDialog;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
