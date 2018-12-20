package com.zyh.wanandroid.ui.category.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zyh.wanandroid.ui.category.list.CategoryListFragment;

import java.util.ArrayList;

/**
 * Time:2018/12/19
 * Author:ZYH
 * Description:
 */
public class FPagerAdapter extends FragmentPagerAdapter{

    private ArrayList<String> titleList;
    private ArrayList<Integer> idList;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public FPagerAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<Integer> idList) {
        super(fm);
        this.titleList = titleList;
        this.idList = idList;
        for (int i = 0; i < idList.size() ; i++) {
            fragments.add(CategoryListFragment.newInstance(idList.get(i)));
        }
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return idList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
