package com.zyh.wanandroid.utils.view

import android.content.Context
import android.util.AttributeSet

class MarqueeTextView : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun isFocused(): Boolean {//必须重写，且返回值是true，表示始终获取焦点
        return true
    }
}
