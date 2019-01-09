package com.zyh.wanandroid.utils.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.zyh.wanandroid.R

/**
 * author : zyh
 * Date : 2019/1/8
 * Description :
 */
class MDialog: Dialog{
    constructor(context: Context) : this(context, R.style.dialog){}
    constructor(context: Context?, themeResId: Int) : super(context, themeResId) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)

        val kitView = findViewById<SpinKitView>(R.id.spin_kit)
        kitView.setIndeterminateDrawable(ThreeBounce())
        setCanceledOnTouchOutside(false)
    }

    /*class Builder(private val context: Context){
        private var mDialog : MDialog? = null
        fun build() : MDialog{
            if (mDialog == null)
                mDialog = MDialog(context)
            return mDialog!!
        }
    }*/

}