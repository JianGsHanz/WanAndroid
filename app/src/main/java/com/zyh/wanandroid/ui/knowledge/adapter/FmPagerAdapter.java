package com.zyh.wanandroid.ui.knowledge.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zyh.wanandroid.ui.knowledge.list.KnowledgeListFragment;

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
