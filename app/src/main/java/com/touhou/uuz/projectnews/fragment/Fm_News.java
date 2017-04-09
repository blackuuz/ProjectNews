package com.touhou.uuz.projectnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.touhou.uuz.projectnews.Adapter.NewsFragmentAdapter;
import com.touhou.uuz.projectnews.R;
import com.touhou.uuz.projectnews.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UUZ on 2017/2/15.
 */

public class Fm_News extends Fragment {
    private View v;
    private NewsFragmentAdapter madapter;

    private TabLayout mtabLayout;
    private ViewPager mviewPager;
    private List<String> titles;//tab标签要显示的文字
    private List<Fragment> mfragments;//ViewPager内Fragment的容器
    private String[] mTypes = {"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
         v = inflater.inflate(R.layout.fm_news, null);
        initwidget();
        initData();
        return v;
    }

    private void initwidget()
    {
        mtabLayout = (TabLayout) v.findViewById(R.id.tablayout_news);
        mviewPager = (ViewPager) v.findViewById(R.id.fm_new_viewpager);
    }

    private void initData()
    {
        titles = new ArrayList<>();
        titles.add("头条");
        titles.add("社会");
        titles.add("国内");
        titles.add("国际");
        titles.add("娱乐");
        titles.add("体育");
        titles.add("军事");
        titles.add("科技");
        titles.add("财经");
        titles.add("时尚");
        mfragments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NewsListFragment fragment = new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", mTypes[i]);
            fragment.setArguments(bundle);
            mfragments.add(fragment);
        }
        madapter = new NewsFragmentAdapter(getFragmentManager());
        madapter.addTitle(titles);
        madapter.addFragment(mfragments);
        mviewPager.setAdapter(madapter);
        mtabLayout.setupWithViewPager(mviewPager);//关联TabLayout和viewpager
    }




}
