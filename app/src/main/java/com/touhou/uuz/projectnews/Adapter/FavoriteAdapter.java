package com.touhou.uuz.projectnews.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.touhou.uuz.projectnews.R;
import com.touhou.uuz.projectnews.entity.NewsInfo;

import java.util.List;

/**
 * Created by UUZ on 2017/2/23.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
private List<NewsInfo> datas;
public void addDatasToAdapter(List<NewsInfo> datas)
{
    this.datas = datas;
}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {   View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_favorite_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
       NewsInfo data =  datas.get(position);
        holder.newsPic.setImageResource(R.mipmap.ic_launcher);
        holder.ftitle.setText(data.title);
        holder.fdate.setText(data.date);
        holder.currenttime.setText(System.currentTimeMillis()+"");

    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newsPic;
        TextView ftitle, fdate, currenttime;


        public ViewHolder(View itemView)
        {
            super(itemView);
            newsPic = (ImageView) itemView.findViewById(R.id.imageView_f);
            ftitle = (TextView) itemView.findViewById(R.id.tv_title_f);
            fdate = (TextView) itemView.findViewById(R.id.tv_date_f);
            currenttime = (TextView) itemView.findViewById(R.id.tv_time_f);

        }
    }
}
