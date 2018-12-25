package com.zyh.wanandroid.ui.web;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import cn.sharesdk.onekeyshare.OnekeyShare;

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
    private String url;
    private String content;

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
        Bundle bundle = getArguments();
        url = bundle.getString("url", "www.wanandroid.com");
        content = bundle.getString("content", "WanAndroid");
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
                share();
                break;
            case R.id.tv_collect:
                ToastUtils.showShortToast("收藏");
                break;
            case R.id.tv_copy:
                copyUrl();
                break;
            case R.id.tv_browser:
                openBrowser();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    private void share() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("来自WanAndroid");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(url);
        // 启动分享GUI
        oks.show(getActivity());
    }

    private void openBrowser() {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void copyUrl() {
        ClipboardManager cm = (ClipboardManager) getActivity()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, url));
        ToastUtils.showShortToast("复制成功");
    }

    public static BottomDialog newInstance(String url,String content){
        BottomDialog bottomDialog = new BottomDialog();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        bundle.putString("content",content);
        bottomDialog.setArguments(bundle);
        return bottomDialog;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
