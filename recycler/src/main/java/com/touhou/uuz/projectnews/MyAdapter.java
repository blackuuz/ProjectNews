package com.touhou.uuz.projectnews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by UUZ on 2017/2/20.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
private List<JavaBean> datas;

    public void addDatas(List<JavaBean> datas)
    {
        this.datas = datas;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView;
        private TextView textView;


        //构造方法内的参数View 通常指的是Recycler内Iiem的最外层布局
        public ViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);

        }
    }
//主要用于初始化ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclr,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
//主要用于设置数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        JavaBean data = datas.get(position);
        holder.imageView.setImageBitmap(data.bitmap);
        holder.textView.setText(data.mtitle);
    }
//获取列表数量
    @Override
    public int getItemCount()
    {
        return datas.size();
    }

}
