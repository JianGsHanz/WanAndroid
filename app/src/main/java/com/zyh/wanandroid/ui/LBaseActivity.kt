package com.zyh.wanandroid.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.Unbinder
import com.common.base.BaseActivity
import com.common.util.StatusBarsUtil
import com.zyh.wanandroid.App
import com.zyh.wanandroid.R
import java.util.*

/**
 * author : zyh
 * Date : 2018/11/29
 * Description :
 */
abstract class LBaseActivity : BaseActivity(){

    lateinit var bind : Unbinder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getInstance().addActivity(this)
        bind = ButterKnife.bind(this)
        steepStatusBar()
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

    /**
     * 沉浸状态栏
     */
    private fun steepStatusBar() {
        if (StatusBarsUtil.setStatusBarDarkTheme(this,true))
            StatusBarsUtil.setStatusBarColor(this, Color.WHITE)
    }

    override fun onDestroy() {
        super.onDestroy()
        bind.unbind()
        App.getInstance().removeActivity(this)
    }
}