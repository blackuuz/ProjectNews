package adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UUZ on 2017/2/13.
 */

public class MyGuideAdapter extends PagerAdapter {
    private List<View> datas = new ArrayList<>();

    public void addViewToAdapter(View view)
    {
        if (view != null)
        {
            datas.add(view);
        }
    }

    @Override
    public int getCount()
    {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
       // super.destroyItem(container, position, object);
        View view = datas.get(position);
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View view = datas.get(position);
        container.addView(view);
        return view;
    }
}
