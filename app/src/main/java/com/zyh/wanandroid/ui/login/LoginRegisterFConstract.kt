package com.zyh.wanandroid.ui.login

import com.common.base.BasePresenter
import com.common.base.BaseView

interface LoginRegisterFConstract{
    interface presenter : BasePresenter{
        fun requestLogin(userName: String,password: String)
        fun requestRegister(username: String,password: String,repassword: String)
    }
    interface view : BaseView{
        fun resultLogin()
        fun resultRegister()
    }
}