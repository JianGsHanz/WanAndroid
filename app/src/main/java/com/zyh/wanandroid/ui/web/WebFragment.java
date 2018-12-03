package com.zyh.wanandroid.ui.web;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.common.base.BaseMvpFragment;
import com.just.agentweb.AgentWeb;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    Unbinder unbinder;
    private static final String URL = "url";

    private static final String CONTENT = "content";

    private static final String ARTICLEID = "articleId";

    private String link,content;

    private int articleId;
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
        AgentWeb.with(this)
                .setAgentWebParent(webLayout,new ConstraintLayout.LayoutParams(-1,-1))
                .useDefaultIndicator()// 使用默认进度条
                .createAgentWeb()//
                .ready()
                .go(link);
        tvTitle.setText(content);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        link = getArguments().getString(URL);
        content = getArguments().getString(CONTENT);
        articleId = getArguments().getInt(ARTICLEID);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_back, R.id.iv_close, R.id.iv_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.iv_close:
                break;
            case R.id.iv_other:
                break;
        }
    }

    public static WebFragment newInstance(String url,String content,int id) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        args.putString(CONTENT, content);
        args.putInt(ARTICLEID,id);
        fragment.setArguments(args);
        return fragment;
    }

}
