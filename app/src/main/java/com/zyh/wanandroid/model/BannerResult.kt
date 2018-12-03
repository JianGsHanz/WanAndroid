package com.zyh.wanandroid.model

import java.nio.ByteOrder

/**
 * author : zyh
 * Date : 2018/12/3
 * Description :
 */

/*{
    "desc": "",
    "id": 4,
    "imagePath": "http://www.wanandroid.com/blogimgs/ab17e8f9-6b79-450b-8079-0f2287eb6f0f.png",
    "isVisible": 1,
    "order": 0,
    "title": "看看别人的面经，搞定面试~",
    "type": 1,
    "url": "http://www.wanandroid.com/article/list/0?cid=73"
}*/
class BannerResult(
        var desc : String,
        var id : Int,
        var imagePath : String,
        var isVisible : Int,
        var order: Int,
        var title : String,
        var type : Int,
        var url : String
)