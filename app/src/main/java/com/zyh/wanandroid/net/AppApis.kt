package com.zyh.wanandroid.net

import com.zyh.wanandroid.model.*
import io.reactivex.Observable
import retrofit2.http.*

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
    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username")username: String,@Field("password") password: String) : Observable<BaseResult<UserResult>>
    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("username")username: String,@Field("password") password: String,
                 @Field("repassword")repassword: String): Observable<BaseResult<UserResult>>
    /**
     * 退出登录
     */
    @GET("user/logout/json")
    fun logout(): Observable<BaseResult<String>>
    /**
     * 导航数据
     */
    @GET("navi/json")
    fun getNavigation() : Observable<NavigationResult>
    /**
     * 体系数据
     */
    @GET("tree/json")
    fun getArticle() : Observable<ArticleResult>
}