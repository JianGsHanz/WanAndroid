package com.zyh.wanandroid.utils.view

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
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

    fun init(context: Context, attributeSet: AttributeSet?){
        LayoutInflater.from(context).inflate(R.layout.custom_setting_layout,null)
        val custom_tv = findViewById<TextView>(R.id.custom_tv)
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.customSettingLayout)
        if (attributes!=null){
            val customTv = attributes.getString(R.styleable.customSettingLayout_text)
            if (TextUtils.isEmpty(customTv))
                custom_tv.text = customTv
            val customTvSize = attributes.getFloat(R.styleable.customSettingLayout_textSize, 14.0F)
            custom_tv.textSize = customTvSize
            val customTvColor = attributes.getColor(R.styleable.customSettingLayout_textColor,Color.BLACK)
            custom_tv.setTextColor(customTvColor)
            val customTvHeight = attributes.getInt(R.styleable.customSettingLayout_layout_height, 48)
            custom_tv.height = customTvHeight
            val customTvDrawableLeft = attributes.getDrawable(R.styleable.customSettingLayout_drawableLeft)
            if (customTvDrawableLeft != null)
            custom_tv.setCompoundDrawables(customTvDrawableLeft,null,null,null)

         attributes.recycle()
        }

    }

}