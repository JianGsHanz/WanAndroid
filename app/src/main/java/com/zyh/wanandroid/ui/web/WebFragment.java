package com.zyh.wanandroid.ui.web;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.common.base.BaseMvpFragment;
import com.common.util.PrefsUtils;
import com.common.util.ToastUtils;
import com.just.agentweb.AgentWeb;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class WebFragment extends BaseMvpFragment<WebFPresenter> implements WebFContract.view {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_other)
    ImageView ivOther;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.web_Layout)
    ConstraintLayout webLayout;
    private static final String URL = "url";
    private static final String CONTENT = "content";
    private static final String ARTICLEID = "articleId";
    private static final String COLLECT = "collect";

    private String link,content;

    private int articleId;
    private AgentWeb agentWeb;
    private boolean collect;
    private BottomDialog bottomDialog;

    @Inject
    public WebFragment(){}
    @Override
    public int getLayoutId() {
        return R.layout.fragment_web;
    }

    @Override
    public void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    public void initViewAndEvent() {
        if (articleId == -1) {
            ivOther.setVisibility(View.INVISIBLE);
        } else {
            ivOther.setVisibility(View.VISIBLE);
        }
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(webLayout, new ConstraintLayout.LayoutParams(-1, -1))
                 .useDefaultIndicator(Color.BLACK)
                 .setWebChromeClient(webChromeClient)
                .createAgentWeb()//
                .ready()
                .go(link);
        tvTitle.setText(content);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        link = bundle.getString(URL);
        content = bundle.getString(CONTENT);
        articleId = bundle.getInt(ARTICLEID);
        collect = bundle.getBoolean(COLLECT, false);

        return rootView;
    }

    @OnClick({R.id.iv_back, R.id.iv_close, R.id.iv_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (!agentWeb.back()){
                    pop();
                }
                break;
            case R.id.iv_close:
                pop();
                break;
            case R.id.iv_other:
                showDialog();
                break;
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (!agentWeb.back()){
            pop();
        }
        return true;
    }

    private WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress >= 100){
                progressBar.setVisibility(View.GONE);
                if (view.canGoBack())
                    ivClose.setVisibility(View.VISIBLE);
                else
                    ivClose.setVisibility(View.GONE);
            }else{
                if (progressBar.getVisibility() == View.GONE)
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (title !=null)
                tvTitle.setText(title);
        }
    };

    private void showDialog() {
        bottomDialog = BottomDialog.newInstance(collect);
        bottomDialog.show(getChildFragmentManager(),"bottomDialog");
        bottomDialog.setOnDialogClick(new BottomDialog.OnDialogClickListener() {
            @Override
            public void onShare() {
                share();
            }

            @Override
            public void onCollect() {
                if (!TextUtils.isEmpty(PrefsUtils.getInstance().getString("userName"))) {
                    if (!collect) {
                        mPresenter.articleCollect(articleId);
                    }
                }else
                    ToastUtils.showShortToast("请先登录！");
            }

            @Override
            public void onCopy() {
                copyUrl();
            }

            @Override
            public void onBrowser() {
                openBrowser();
            }
        });
    }

    private void share() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("来自WanAndroid");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(link);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(link);
        // 启动分享GUI
        oks.show(getActivity());
    }

    private void openBrowser() {
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void copyUrl() {
        ClipboardManager cm = (ClipboardManager) getActivity()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, link));
        ToastUtils.showShortToast("复制成功");
    }

    @Override
    public void onCollectSuccess() {
        ToastUtils.showShortToast("收藏成功");
        collect = true;
        bottomDialog.collectUrl(collect);
    }

    public static WebFragment newInstance(String url, String content, int id,boolean collect) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        args.putString(CONTENT, content);
        args.putInt(ARTICLEID,id);
        args.putBoolean(COLLECT,collect);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        agentWeb.getWebLifeCycle().onDestroy();
    }

}
