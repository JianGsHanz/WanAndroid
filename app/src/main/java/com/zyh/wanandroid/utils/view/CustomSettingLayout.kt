package com.zyh.wanandroid.utils.view

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.TextView
import com.common.util.SizeUtil.px2sp
import com.zyh.wanandroid.R

/**
 * author : zyh
 * Date : 2018/12/10
 * Description :
 */
class CustomSettingLayout : ConstraintLayout{
    constructor(context: Context): this(context,null){
    }
    constructor(context: Context,attributeSet: AttributeSet?): this(context,attributeSet,0){
    }
    constructor(context: Context,attributeSet: AttributeSet?, defStyleAttr: Int): super(context,attributeSet,defStyleAttr){
        init(context,attributeSet)
    }

    private fun init(context: Context, attributeSet: AttributeSet?){
        LayoutInflater.from(context).inflate(R.layout.custom_setting_layout,this,true)
        val custom_cl = findViewById<ConstraintLayout>(R.id.custom_cl)
        val custom_tv = findViewById<TextView>(R.id.custom_tv)
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.CustomSettingLayout)
        if (attributes!=null){
            val customTv = attributes.getString(R.styleable.CustomSettingLayout_text)
            if (!TextUtils.isEmpty(customTv))
                custom_tv.text = customTv
            val customTvSize = attributes.getDimensionPixelSize(R.styleable.CustomSettingLayout_textSize, 14)
            custom_tv.textSize = px2sp(context,customTvSize.toFloat())
            val customTvColor = attributes.getColor(R.styleable.CustomSettingLayout_textColor,Color.BLACK)
            custom_tv.setTextColor(customTvColor)
            val customTvDrawableLeft = attributes.getDrawable(R.styleable.CustomSettingLayout_drawableLeft)
            if (customTvDrawableLeft != null) {
                customTvDrawableLeft.setBounds(0,0,customTvDrawableLeft.minimumWidth,customTvDrawableLeft.minimumHeight)
                custom_tv.setCompoundDrawables(customTvDrawableLeft, null, null, null)
            }
            val customTvisibilityLine = attributes.getBoolean(R.styleable.CustomSettingLayout_visibilityLine,true)
            if (!customTvisibilityLine) custom_cl.background = null
         attributes.recycle()
        }

    }

       private fun  px2sp(context: Context, pxValue: Float):Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return pxValue / fontScale + 0.5f
    }
}