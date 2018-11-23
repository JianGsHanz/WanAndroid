package com.zyh.wanandroid.di.module;

import com.zyh.wanandroid.di.scope.GlobalApis;
import com.zyh.wanandroid.net.AppApis;
import com.zyh.wanandroid.net.OkHttpHelper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZYH on 2018/6/20.
 */
@Module
public class ApiModule {

    public Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    @GlobalApis
    @Provides
    @Named("Apis")
    public Retrofit provideAppRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, AppApis.Companion.getBASE_URL());
    }


    @GlobalApis
    @Provides
    public AppApis provideAppService(@Named("Apis") Retrofit retrofit) {
        return retrofit.create(AppApis.class);
    }
    @GlobalApis
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return OkHttpHelper.getInstance().getOkHttpClient();
    }

    @GlobalApis
    @Provides
    public Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }
}
