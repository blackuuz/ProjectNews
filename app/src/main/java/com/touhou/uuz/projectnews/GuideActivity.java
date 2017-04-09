package com.touhou.uuz.projectnews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.touhou.uuz.projectnews.Adapter.GuideAdapter;

import com.touhou.uuz.projectnews.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity {
    @Bind(R.id.guide_vp) ViewPager viewPager ;//绑定ViewPager的资源ID
    @Bind({R.id.iv1_guid,R.id.iv2_guid,R.id.iv3_guid,R.id.iv4_guid})
    ImageView []imgs = new ImageView[4];
    // private ViewPager viewPager;
    private GuideAdapter adapter;
    private LayoutInflater inflater;
    //private ImageView imgs[] = new ImageView[4];
    //private TextView tv_skip;
    private boolean isFirst;
    private boolean  isScrolled;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);//初始化ButterKnife,  将视图和空间进行绑定

        isFirstRunning();
        initwidget();

        //getImages();

    }
    private void initwidget()
    {
        Log.d("琪露诺","initwidget——");
        //viewPager = (ViewPager) findViewById(R.id.guide_vp);

        adapter = new GuideAdapter();
        getViews();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(listener);
    }


    private void getViews()
    {
        Log.d("琪露诺","getViews——");
        inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.item1_viewpager_guide, null);
        View view2 = inflater.inflate(R.layout.item2_viewpager_guide, null);
        View view3 = inflater.inflate(R.layout.item3_viewpager_guide, null);
        View view4 = inflater.inflate(R.layout.item4_viewpager_guide, null);
        adapter.addViewToAdapter(view1);
        adapter.addViewToAdapter(view2);
        adapter.addViewToAdapter(view3);
        adapter.addViewToAdapter(view4);
        adapter.notifyDataSetChanged();
    }




    private  ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener()
    {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {

        }

        @Override
        public void onPageSelected(int position)
        {
            Log.d("琪露诺","ViewPager.OnPageChangeListener------");
            // 遍历 图片数组
            for (int i = 0; i < imgs.length; i++)
            {
                imgs[i].setImageResource(R.drawable.adware_style_default);// 先设置成默认的样子
            }
            imgs[position].setImageResource(R.drawable.adware_style_selected);
            // 判断如果滑到最后一张，则让文本显示出来
//            if (position >= imgs.length - 1)
//            {
//                tv_skip.setVisibility(View.VISIBLE);
//            } else
//            {
//                tv_skip.setVisibility(View.INVISIBLE);
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
            switch (state)
            {
                case ViewPager.SCROLL_STATE_DRAGGING://正在滑动
                    isScrolled = false;
                   break;
                case ViewPager.SCROLL_STATE_SETTLING://滑动完成
                    isScrolled = true;
                    break;
                case ViewPager.SCROLL_STATE_IDLE://ds nothing
                    if(viewPager.getCurrentItem() == adapter.getCount() - 1&& !isScrolled)
                    {
                        startActivity(LaunchActivity.class);
                        GuideActivity.this.finish();
                    }
                    isScrolled = true;
                    break;
            }
        }
    };


//
    private void isFirstRunning()
    {
        // name 用于指定xml文件的名称
        // Context.MODE_PRIVATE;
        // Context.MODE_APPEND;
        // Context.MODE_WORLD_READABLE;
        // Context.MODE_WORLD_WRITEABLE;
        Log.d("琪露诺"," isFirstRunning()");

        SharedPreferences sp = getSharedPreferences("isFirstRunning", Context.MODE_PRIVATE);
        //利用sp对象去获取数据，如果没有取到则默认为true
        isFirst = sp.getBoolean("isFirst", true);
        //调用sp.edit()方法获取一个Editor对象用于向sp内存入数据
        SharedPreferences.Editor editor = sp.edit();
        //向sp内存入数据
        editor.putBoolean("isFirst", false);
        //提交此次操作
        editor.commit();
        if (!isFirst)
        {
            Intent intent = new Intent(GuideActivity.this,LaunchActivity.class);
            startActivity(intent);
            GuideActivity.this.finish();
        }
    }
}
