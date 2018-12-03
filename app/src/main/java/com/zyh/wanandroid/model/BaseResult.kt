package com.zyh.wanandroid.model

/**
 * author : zyh
 * Date : 2018/12/3
 * Description :
 */
/*{
    "data": ...,
    "errorCode": 0,
    "errorMsg": ""
}*/
class BaseResult<T>(var data : T? = null,var errorCode : Int =  -1,var errorMsg : String = "")