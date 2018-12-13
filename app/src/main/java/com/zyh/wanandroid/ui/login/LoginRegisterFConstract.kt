package com.zyh.wanandroid.ui.login

import com.common.base.BasePresenter
import com.common.base.BaseView
import com.zyh.wanandroid.model.UserResult

interface LoginRegisterFConstract{
    interface presenter : BasePresenter{
        fun requestLogin(userName: String,password: String)
        fun requestRegister(username: String,password: String,repassword: String)
    }
    interface view : BaseView{
        fun loginSuccess(userResult: UserResult)
        fun loginFail(errorMsg: String)
        fun registerSuccess(userResult: UserResult)
        fun registerFail(errorMsg: String)
    }
}