package com.zyh.wanandroid.ui.home;

import com.common.base.AbsBasePresenter;
import com.common.util.RxUtils;
import com.zyh.wanandroid.model.BannerResult;
import com.zyh.wanandroid.model.BaseResult;
import com.zyh.wanandroid.model.HomeResult;
import com.zyh.wanandroid.net.AppApis;
import com.zyh.wanandroid.ui.main.MainActivity;
import com.zyh.wanandroid.ui.splash.SplashActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
public class HomeFPresenter extends AbsBasePresenter<HomeFConstract.view> implements HomeFConstract.presenter {
    private AppApis appApis;
    private boolean isRefresh = true;
    private int currentPage;

    @Inject
    public HomeFPresenter(AppApis appApis) {
        this.appApis = appApis;
    }

    @Override
    public void loadData() {
        registerRx(appApis.getBannerList()
                .compose(RxUtils.<BaseResult<List<BannerResult>>>rxSchedulerHelpe())
                .subscribe(new Consumer<BaseResult<List<BannerResult>>>() {
                    @Override
                    public void accept(BaseResult<List<BannerResult>> listBaseResult) throws Exception {
                        mView.getBannerSuccess(listBaseResult.getData());
                    }
                }));

    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
        getHomeListData(currentPage);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getHomeListData(currentPage);
    }

    private void getHomeListData(int currentPage) {
        registerRx(appApis.getHomeArticleList(currentPage)
                .compose(RxUtils.<BaseResult<HomeResult>>rxSchedulerHelpe())
                .subscribe(new Consumer<BaseResult<HomeResult>>() {
                    @Override
                    public void accept(BaseResult<HomeResult> homeResultBaseResult) throws Exception {
                        mView.getHomeListSuccess(homeResultBaseResult.getData(),isRefresh);
                    }
                }));
    }

    @Override
    public void releaseData() {

    }


}
