package com.touhou.uuz.projectnews.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UUZ on 2017/2/13.
 */

public class GuideAdapter extends PagerAdapter {


    private List<View> views = new ArrayList();
    //添加数据到Adapter
    public void addViewToAdapter(View view)
    {
        if (views != null)
        {
            views.add(view);
        }
    }



    @Override
    public int getCount()
    {
        return views.size();
    }

    @Override//判断view和object是否有关联
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        //super.destroyItem(container, position, object);
        View view = views.get(position);//获取当前位置的view
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        //获取当前位置显示的view
        View view = views.get(position);
        //添加到ViewGroup里
        container.addView(view);
        return view;
    }
}
