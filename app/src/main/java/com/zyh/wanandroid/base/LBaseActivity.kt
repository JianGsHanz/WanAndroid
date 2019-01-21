package com.zyh.wanandroid.base

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.common.base.BaseActivity
import com.common.base.BaseView
import com.common.util.StatusBarsUtil
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.zyh.wanandroid.R

/**
 * author : zyh
 * Date : 2019/1/15
 * Description :
 */
abstract class LBaseActivity : BaseActivity(), BaseView {

    companion object {
        const val NORMAL_STATE : Int= 0
        const val LOADING_STATE = 1
        const val EMPTY_STATE = 2
        const val ERROR_STATE = 3
    }
    private var currentState = NORMAL_STATE
    private lateinit var mNormalView : ViewGroup
    private lateinit var mErrorView : View
    private lateinit var mLoadingView : View
    private lateinit var mEmptyView : View
    private lateinit var tvErrMsg : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        steepStatusBar()
        if (this == null)
            throw IllegalStateException("Activity cannot be empty")
        mNormalView = this.findViewById<ViewGroup>(R.id.normal_view)
        if (mNormalView == null)
            throw IllegalStateException("There must be no mNormalView in the activity")
        if(mNormalView.parent !is ViewGroup){
            throw  IllegalStateException("The parent layout of mNormalView must belong to the viewGroup")
        }
        val parent = mNormalView.parent as ViewGroup
        View.inflate(this,R.layout.view_loading,parent)
        View.inflate(this,R.layout.view_error,parent)
        View.inflate(this,R.layout.view_empty,parent)

        mLoadingView = parent.findViewById<View>(R.id.loading_group)
        val loadView = mLoadingView.findViewById<SpinKitView>(R.id.lv_load)
        loadView.setIndeterminateDrawable(ThreeBounce())
        mErrorView = parent.findViewById<View>(R.id.error_group)
        mEmptyView = parent.findViewById<View>(R.id.empty_group)
        tvErrMsg = parent.findViewById(R.id.tv_err_msg)

        mErrorView.setOnClickListener {
            reLoad()
        }
    }
    override fun showNormal() {
        if (currentState == NORMAL_STATE) return
        hideCurrentView()
        currentState = NORMAL_STATE
        mNormalView.visibility = View.VISIBLE
    }

    override fun showError(err : String) {
        if (currentState == ERROR_STATE) return
        hideCurrentView()
        currentState = ERROR_STATE
        tvErrMsg.text = err
        mErrorView.visibility = View.VISIBLE
    }

    override fun showLoading() {
        if (currentState == LOADING_STATE) return
        hideCurrentView()
        currentState = LOADING_STATE
        mLoadingView.visibility = View.VISIBLE
    }

    override fun showEmpty() {
        if (currentState == EMPTY_STATE) return
        hideCurrentView()
        currentState = EMPTY_STATE
        mEmptyView.visibility = View.VISIBLE
    }

    private fun hideCurrentView(){
        when(currentState){
            NORMAL_STATE -> {
                if (mNormalView == null) {
                    return
                }
                mNormalView.visibility = View.GONE
            }
            LOADING_STATE ->
                mLoadingView.visibility = View.GONE
            EMPTY_STATE ->
                mEmptyView.visibility = View.GONE
            ERROR_STATE ->
                mErrorView.visibility = View.GONE
        }
    }

    override fun reLoad() {

    }

    /**
     * 沉浸状态栏
     */
    private fun steepStatusBar() {
        if (StatusBarsUtil.setStatusBarDarkTheme(this,true))
            StatusBarsUtil.setStatusBarColor(this, Color.WHITE)
    }
    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    fun startActivityForResult(cls :Class<*>, bundle : Bundle?,requestCode :Int) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    fun startActivity(cls :Class<*>, bundle : Bundle?) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim)
    }
}