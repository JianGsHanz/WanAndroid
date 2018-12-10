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
    constructor(context: Context): super(context){
        init(context,null)
    }
    constructor(context: Context,attributeSet: AttributeSet): super(context,attributeSet){
        init(context,attributeSet)
    }
    constructor(context: Context,attributeSet: AttributeSet, defStyleAttr: Int): super(context,attributeSet,defStyleAttr){
        init(context,attributeSet)
    }

    fun init(context: Context, attributeSet: AttributeSet?){
        LayoutInflater.from(context).inflate(R.layout.custom_setting_layout,null)
        val custom_tv = findViewById<TextView>(R.id.custom_tv)
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.custom_setting_attr)
        if (attributes!=null){
            val customTv = attributes.getString(R.styleable.CustomSettingLayout_text)
            if (TextUtils.isEmpty(customTv))
                custom_tv.text = customTv

            val customTvColor = attributes.getColor(R.styleable.CustomSettingLayout_textColor,Color.BLACK)
            if (customTvColor != 0)
//                custom_tv.textColors = customTvColor
        }

    }

}