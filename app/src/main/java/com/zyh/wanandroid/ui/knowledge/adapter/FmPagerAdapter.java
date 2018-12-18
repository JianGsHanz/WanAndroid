package com.zyh.wanandroid.ui.knowledge.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zyh.wanandroid.ui.knowledge.article.KnowledgeListFragment;

import java.util.ArrayList;

public class FmPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> titleList;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public FmPagerAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<Integer> idList) {
        super(fm);
        this.titleList = titleList;
        for (int i = 0; i <titleList.size() ; i++) {
            fragments.add(KnowledgeListFragment.newInstance(idList.get(i)));
        }
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
