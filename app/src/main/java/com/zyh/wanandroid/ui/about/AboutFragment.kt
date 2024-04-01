package com.zyh.wanandroid.ui.about

import android.os.Bundle
import androidx.core.text.HtmlCompat
import androidx.core.widget.NestedScrollView
import androidx.core.widget.TextViewCompat
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.common.base.BaseFragment
import com.zyh.wanandroid.App
import com.zyh.wanandroid.R
import com.zyh.wanandroid.utils.view.slidenested.SlideNestedPanelLayout
import com.zyh.wanandroid.utils.view.slidenested.StateCallback
import me.yokeyword.fragmentation.ISupportFragment
import javax.inject.Inject

class AboutFragment@Inject constructor() : BaseFragment(),ISupportFragment {


    lateinit var mPanelLayout: SlideNestedPanelLayout
    lateinit var nestedScrollView: NestedScrollView
    lateinit var tvContent: TextView
    override fun getLayoutId(): Int = R.layout.fragment_about

    override fun initInject() {
        App.getInstance().fragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mPanelLayout = view!!.findViewById<SlideNestedPanelLayout>(R.id.slideNestedPanelLayout)
        nestedScrollView = view!!.findViewById<NestedScrollView>(R.id.nestedScrollView)
        tvContent = view!!.findViewById<TextView>(R.id.tv_content)
        return view
    }
    override fun initView() {

    }

    override fun initViewAndEvent() {
        nestedScrollView!!.animate().translationY(-150F).alpha(0.8F).duration = 500
        tvContent!!.text = HtmlCompat.fromHtml(getString(R.string.about_content), HtmlCompat.FROM_HTML_MODE_COMPACT)
        mPanelLayout!!.setStateCallback(object : StateCallback {
            override fun onCollapsedState() {
            }

            override fun onExpandedState() {
            }

        })
    }
}
