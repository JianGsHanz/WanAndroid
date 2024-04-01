package com.zyh.wanandroid.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.base.AbsBasePresenter;
import com.common.base.BaseFragment;
import com.common.base.BaseView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.utils.view.MDialog;

import javax.inject.Inject;

/**
 * author : zyh
 * Date : 2019/1/16
 * Description :
 */
public abstract class LBaseMvpFragment<T extends AbsBasePresenter> extends BaseFragment implements BaseView{

    @Inject
    protected T mPresenter;

    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;
    public static final int EMPTY_STATE = 3;

    private View mErrorView;
    private View mLoadingView;
    private View mEmptyView;
    private ViewGroup mNormalView;
    private int currentState = NORMAL_STATE;
    private TextView tvErrMsg;
    private MDialog mDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null)
            mPresenter.attachView(this);
        mPresenter.loadData();
    }

    @Override
    protected void initView() {
        if(getView() == null){
            throw new IllegalStateException("Activity cannot be empty");
        }
        mNormalView = getView().findViewById(R.id.normal_view);
        if(mNormalView  == null){
            throw new IllegalStateException("There must be no mNormalView in the activity");
        }
        if(!(mNormalView.getParent() instanceof ViewGroup)){
            throw new IllegalStateException("The parent layout of mNormalView must belong to the viewgroup");
        }
        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(getActivity(), R.layout.view_loading, parent);
        View.inflate(getActivity(), R.layout.view_error, parent);
        View.inflate(getActivity(), R.layout.view_empty, parent);
        mLoadingView = parent.findViewById(R.id.loading_group);
        SpinKitView loadView = mLoadingView.findViewById(R.id.lv_load);
        loadView.setIndeterminateDrawable(new ThreeBounce());
        mErrorView = parent.findViewById(R.id.error_group);
        mEmptyView = parent.findViewById(R.id.empty_group);
        tvErrMsg = parent.findViewById(R.id.tv_err_msg);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoad();
            }
        });
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);
        mDialog = new MDialog(getActivity());
    }

    @Override
    public void showNormal() {
        if(currentState == NORMAL_STATE){
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String err) {
        if(currentState == ERROR_STATE){
            return;
        }
        hideCurrentView();
        currentState = ERROR_STATE;
        tvErrMsg.setText(err);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        if(currentState == LOADING_STATE){
            return;
        }
        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        if(currentState == EMPTY_STATE){
            return;
        }
        hideCurrentView();
        currentState = EMPTY_STATE;
        mEmptyView.setVisibility(View.VISIBLE);
    }

    private void hideCurrentView() {
        switch (currentState) {
            case NORMAL_STATE:
                if (mNormalView == null) {
                    return;
                }
                mNormalView.setVisibility(View.GONE);
                break;
            case LOADING_STATE:
                mLoadingView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.GONE);
                break;
            case EMPTY_STATE:
                mEmptyView.setVisibility(View.GONE);
            default:
                break;
        }
    }


    @Override
    public void reLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mPresenter.releaseData();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
