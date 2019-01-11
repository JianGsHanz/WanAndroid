package com.zyh.wanandroid.utils

import android.content.Context
import com.zyh.wanandroid.utils.view.MDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * author : zyh
 * Date : 2019/1/8
 * Description :
 */
abstract class CustomConsumer<T> :Observer<T>{
    var mDialog: MDialog?=null
    var context: Context
    constructor(context: Context){
        this.context = context
    }
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {
        if (mDialog == null)
        mDialog = MDialog(context)
        mDialog!!.show()
        onDisposable(d)
    }

    override fun onNext(t: T) {
        if (mDialog!!.isShowing)
            mDialog!!.hide()
        accept(t)
    }

    override fun onError(e: Throwable) {
        if (mDialog!!.isShowing)
            mDialog!!.hide()
    }
    abstract fun accept(t: T)
    abstract fun onDisposable(d: Disposable)
}