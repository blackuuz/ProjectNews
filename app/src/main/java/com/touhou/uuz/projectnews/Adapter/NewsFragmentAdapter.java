package com.touhou.uuz.projectnews.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by UUZ on 2017/2/20.
 */

public class NewsFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mfragments;
    private List<String> title;


    public NewsFragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public void addFragment(List<Fragment> mfragments)
    {
        this.mfragments = mfragments;
    }

    public void addTitle(List<String> title)
    {
        this.title = title;
    }



    @Override
    public Fragment getItem(int position)
    {
        return mfragments.get(position);
    }

    @Override
    public int getCount()
    {
        return title.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return title.get(position);
    }
}
