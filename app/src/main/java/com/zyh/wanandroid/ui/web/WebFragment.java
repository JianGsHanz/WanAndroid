package com.zyh.wanandroid.ui.web;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.common.base.BaseMvpFragment;
import com.common.util.ToastUtils;
import com.just.agentweb.AgentWeb;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

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

    private String link,content;

    private int articleId;
    private AgentWeb agentWeb;

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
                 .useDefaultIndicator()
                 .setWebChromeClient(webChromeClient)
                .createAgentWeb()//
                .ready()
                .go(link);
        tvTitle.setText(content);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        link = getArguments().getString(URL);
        content = getArguments().getString(CONTENT);
        articleId = getArguments().getInt(ARTICLEID);
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
                ToastUtils.showShortToast("未完待续...");
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



    public static WebFragment newInstance(String url, String content, int id) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        args.putString(CONTENT, content);
        args.putInt(ARTICLEID,id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        agentWeb.getWebLifeCycle().onDestroy();
    }
}
