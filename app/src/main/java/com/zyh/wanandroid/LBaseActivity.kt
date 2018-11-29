package com.zyh.wanandroid

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.common.base.BaseActivity

/**
 * author : zyh
 * Date : 2018/11/29
 * Description :
 */
class LBaseActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getInstance().addActivity(this)
        steepStatusBar()
    }
    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    fun startActivityForResult(cls :Class<*>, bundle : Bundle,requestCode :Int) {
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
    fun startActivity(cls :Class<*>, bundle : Bundle) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 沉浸状态栏
     */
    fun steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            window.addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 透明导航栏
            window.addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        App.getInstance().removeActivity(this)
    }
}