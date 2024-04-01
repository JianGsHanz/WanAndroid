package com.zyh.wanandroid.utils.view

import android.content.Context
import android.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import com.zyh.wanandroid.R

/**
 * author : zyh
 * Date : 2018/12/10
 * Description :设置界面 ________>
 */
class CustomSettingLayout : ConstraintLayout {
    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {}

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        init(context, attributeSet)
    }

    private fun init(context: Context, attributeSet: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.custom_setting_layout, this, true)
        val custom_cl = findViewById<ConstraintLayout>(R.id.custom_cl)
        val custom_tv = findViewById<TextView>(R.id.custom_tv)
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.CustomSettingLayout)

        attributes?.let {
            val customTv = it.getString(R.styleable.CustomSettingLayout_text)
            if (!TextUtils.isEmpty(customTv))
                custom_tv.text = customTv

            val customTvSize = it.getDimensionPixelSize(R.styleable.CustomSettingLayout_textSize, 14)
            custom_tv.textSize = px2sp(context, customTvSize.toFloat())

            val customTvColor = it.getColor(R.styleable.CustomSettingLayout_textColor, Color.BLACK)
            custom_tv.setTextColor(customTvColor)

            val customTvDrawableLeft = it.getDrawable(R.styleable.CustomSettingLayout_drawableLeft)
            customTvDrawableLeft?.setBounds(0, 0, customTvDrawableLeft.minimumWidth, customTvDrawableLeft.minimumHeight)
            custom_tv.setCompoundDrawables(customTvDrawableLeft, null, null, null)

            val customTvisibilityLine = it.getBoolean(R.styleable.CustomSettingLayout_visibilityLine, true)
            if (!customTvisibilityLine) custom_cl.setBackgroundColor(Color.WHITE)

            it.recycle()
        }


    }

    private fun px2sp(context: Context, pxValue: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return pxValue / fontScale + 0.5f
    }
}