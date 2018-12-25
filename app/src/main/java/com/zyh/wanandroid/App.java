package com.zyh.wanandroid;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.common.app.ActivityLifecycleManager;
import com.common.app.AppComponent;
import com.common.app.AppModule;
import com.common.app.DaggerAppComponent;
import com.common.util.CrashHandler;
import com.common.util.NetworkUtils;
import com.common.util.PrefsUtils;
import com.common.util.Utils;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.mob.MobSDK;
import com.zyh.wanandroid.di.component.ActivityComponent;
import com.zyh.wanandroid.di.component.ApiComponent;
import com.zyh.wanandroid.di.component.DaggerActivityComponent;
import com.zyh.wanandroid.di.component.DaggerApiComponent;
import com.zyh.wanandroid.di.component.DaggerFragmentComponent;
import com.zyh.wanandroid.di.component.FragmentComponent;
import com.zyh.wanandroid.di.module.ActivityModule;
import com.zyh.wanandroid.di.module.ApiModule;
import com.zyh.wanandroid.di.module.FragmentModule;
import com.zyh.wanandroid.utils.CustomBitmapMemoryCacheParamsSupplier;

import java.util.HashSet;
import java.util.Set;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * author : zyh
 * Date : 2018/11/26
 * Description :
 */
public class App extends Application {

    private static App app;
    private AppComponent build;
    private Set<Activity> allActivities;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;


        registerActivityLifecycleCallbacks(new ActivityLifecycleManager());
        Utils.init(this);
        MobSDK.init(this);
        initPrefs();
        initNetwork();
        initCrashHandler();
        initFresco();
        initFragmentation();
        if (build == null)
        build = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    private void initFragmentation() {
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.NONE)
                // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                // false时，不会抛出，会捕获，可以在handleException()里监听到
                .debug(BuildConfig.DEBUG)
                // 在debug=false时，即线上环境时，上述异常会被捕获并回调ExceptionHandler
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 建议在该回调处上传至我们的Crash监测服务器
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
    }

    public void initFresco() {
        //当内存紧张时采取的措施
        MemoryTrimmableRegistry memoryTrimmableRegistry = NoOpMemoryTrimmableRegistry.getInstance();
        memoryTrimmableRegistry.registerMemoryTrimmable(new MemoryTrimmable() {
            @Override
            public void trim(MemoryTrimType trimType) {
                final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();
                Log.d("Fresco", String.format("onCreate suggestedTrimRatio : %d", suggestedTrimRatio));
                if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == suggestedTrimRatio
                        ) {
                    //清除内存缓存
                    Fresco.getImagePipeline().clearMemoryCaches();
                }
            }
        });
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setResizeAndRotateEnabledForNetwork(true)
                .setBitmapMemoryCacheParamsSupplier(new CustomBitmapMemoryCacheParamsSupplier((ActivityManager) getSystemService(ACTIVITY_SERVICE)))
                .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(this, config);
    }

    /**
     * 初始化网络监听
     */
    private void initNetwork() {
        NetworkUtils.startNetService(getApplicationContext());
    }
    /**
     * 初始化崩溃日志
     */
    private void initCrashHandler() {
        CrashHandler.getInstance().init(getApplicationContext());
    }
    /**
     * 初始化sp
     */
    private void initPrefs() {
        PrefsUtils.init(this, getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }
    /**
     * 增加Activity
     *
     * @param act act
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    /**
     * 移除Activity
     *
     * @param act act
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }
    public static synchronized App getInstance(){
        return app;
    }

    public ApiComponent getApiComponent(){
        return DaggerApiComponent.builder().appComponent(build).apiModule(new ApiModule()).build();
    }

    public ActivityComponent getActivityComponent(){
        return DaggerActivityComponent.builder().apiComponent(getApiComponent()).activityModule(new ActivityModule()).build();
    }

    public FragmentComponent getFragmentComponent(){
        return DaggerFragmentComponent.builder().apiComponent(getApiComponent()).fragmentModule(new FragmentModule()).build();
    }
}

