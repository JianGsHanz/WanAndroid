package com.zyh.wanandroid.ui.category;

import com.common.base.BaseMvpFragment;
import com.zyh.wanandroid.App;
import com.zyh.wanandroid.R;

import javax.inject.Inject;

/**
 * author : zyh
 * Date : 2018/11/30
 * Description :
 */
public class CategoryFragment extends BaseMvpFragment<CategoryFPresenter> implements  CategoryFConstract.view{

    @Inject
    public CategoryFragment(){}
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initInject() {
        App.getInstance().getFragmentComponent().inject(this);
    }

    @Override
    protected void initViewAndEvent() {

    }
}
