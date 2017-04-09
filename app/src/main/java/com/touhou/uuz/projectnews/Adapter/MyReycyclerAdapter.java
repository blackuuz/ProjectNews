package com.touhou.uuz.projectnews.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.touhou.uuz.projectnews.R;
import com.touhou.uuz.projectnews.entity.NewsInfo;
import com.touhou.uuz.projectnews.util.ImageLoadUtils;

/**
 * Created by UUZ on 2017/2/20.
 */

public class MyReycyclerAdapter extends RecyclerView.Adapter<MyReycyclerAdapter.ViewHolder> {
    private List<NewsInfo> datas;
    private OnRCVItemClickListener listener;//声明监听器
    private ImageLoadUtils imageLoadUtils;
    public void addDatas(List<NewsInfo> datas)
    {
        this.datas = datas;
    }

    /**
     * 监听recyclerview 的item 的监听器
     */
    public interface OnRCVItemClickListener {
        void onItemClick(NewsInfo info);//将网址传递下去
    }

    public void setOnRCVItemClickListener(OnRCVItemClickListener l)
    {
        this.listener = l;
    }

    public  MyReycyclerAdapter(Context context)
    {
         imageLoadUtils = new ImageLoadUtils(context);

    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView mdate;
        private View newsview;



        //构造方法内的参数View 通常指的是Recycler内Iiem的最外层布局
        public ViewHolder(View view)
        {
            super(view);
            newsview = view;
            imageView = (ImageView) view.findViewById(R.id.imageView);
            title = (TextView) view.findViewById(R.id.tv_title);
            mdate = (TextView) view.findViewById(R.id.tv_date);

        }


    }

    //主要用于初始化ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_item, null);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.newsview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //让别人知道我点击了这个view
                if (listener != null) {
                    //获取当前item的position
                    int position = viewHolder.getAdapterPosition();
                    //获取当前position的实体类对象的url
                    //  String newsUrl = datas.get(position).newsContentUrl;
                    NewsInfo info = datas.get(position);
                    listener.onItemClick(info);

                }
            }
        });
        return viewHolder;
    }

    //主要用于设置数据
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        NewsInfo data = datas.get(position);
      Bitmap  bitmap = imageLoadUtils.loadImage(data.picUrl, new ImageLoadUtils.OnLoadImageListener() {
            @Override
            public void onImageLoadOK(String url, Bitmap bitmap)
            {
                holder.imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onImageLoadError(String url)
            {
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
              //  Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
            }
        });

        //holder.imageView.setImageResource(R.mipmap.ic_launcher);
        // holder.
        holder.imageView.setImageBitmap(bitmap);
        holder.title.setText(data.title);
        holder.mdate.setText(data.date);

    }

    //获取列表数量
    @Override
    public int getItemCount()
    {
        return datas.size();
    }

}
