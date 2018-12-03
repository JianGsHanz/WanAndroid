package com.zyh.wanandroid.net

import com.zyh.wanandroid.model.BannerResult
import com.zyh.wanandroid.model.BaseResult
import com.zyh.wanandroid.model.HomeResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * author : zyh
 * Date : 2018/11/23
 * Description :
 */
interface AppApis{
    companion object {
        val BASE_URL: String = "http://www.wanandroid.com/"
    }

    /**
     * 首页banner
     */
    @GET("banner/json")
    fun getBannerList(): Observable<BaseResult<List<BannerResult>>>

    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
    fun getHomeArticleList(@Path("page")num : Int) : Observable<BaseResult<HomeResult>>

}