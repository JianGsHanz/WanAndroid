package com.zyh.wanandroid.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import com.common.base.IBaseMvpActivity;
import com.common.util.LogUtils;
import com.common.util.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.ui.LBaseActivity;
import com.zyh.wanandroid.ui.main.MainActivity;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author : zyh
 * Date : 2018/11/29
 * Description :
 */
public class SplashActivity extends LBaseActivity implements IBaseMvpActivity<SplashPresenter>,SplashContract.view {

    @Inject
    SplashPresenter presenter;
    private Disposable subscribe;
    private RxPermissions permissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissions = new RxPermissions(this);
        subscribe = permissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            presenter.loadData();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                            finish();
                        } else {
                            // 用户拒绝了该权限，而且选中『不再询问』
                            Intent intent = new Intent();  intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                            ToastUtils.showLongToast("请打开相应权限,以正常使用!");
                        }

                    }
                });
    }

    @Override
    public void initViewAndEvent() {

    }

    @Override
    public SplashPresenter getPresenter() {
        return presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initInject() {
        App.getInstance().getActivityComponent().inject(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        subscribe = permissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            presenter.loadData();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                            finish();
                        } else {
                            // 用户拒绝了该权限，而且选中『不再询问』
                            finish();
                        }

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribe.dispose();
    }

    @Override
    public void result() {
        startActivity(MainActivity.class,null);
        finish();
    }
}
