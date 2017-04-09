package com.touhou.uuz.projectnews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.touhou.uuz.projectnews.Adapter.MyReycyclerAdapter;
import com.touhou.uuz.projectnews.MainActivity;
import com.touhou.uuz.projectnews.R;
import com.touhou.uuz.projectnews.config.NewsConfig;
import com.touhou.uuz.projectnews.entity.NewsInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by UUZ on 2017/2/20.
 */

public class NewsListFragment extends Fragment {
    private View view;
    private String mType;
    private RecyclerView recyclerView;

    //  private TextView tv_cs;
    private String jsonData;//网络请求返回的JSON数据
    private List<NewsInfo> datas;
    private MyReycyclerAdapter adapter;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 1) {
                datas = (List<NewsInfo>) msg.obj;

                adapter.addDatas(datas);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fm_newslist, null);

        mType = getArguments().getString("type");
        //  initData();
        initwidget();
        return view;
    }

    private void initwidget()
    {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_newslist);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new MyReycyclerAdapter(this.getContext());
        adapter.setOnRCVItemClickListener(new MyReycyclerAdapter.OnRCVItemClickListener() {
            @Override
            public void onItemClick(NewsInfo info)
            {
                NewsContentFragment f = new NewsContentFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("newsContentUrl",info);
               /// bundle.putString("newsContentUrl",url);
                f.setArguments(bundle);
                //跳转或者是切换fragment-----Fragment之间的通讯
                ((MainActivity)getActivity()).changeMenuClick(f);

            }
        });
        requestNetwork();


//        adapter = new MyReycyclerAdapter();
//        adapter.addDatas(datas);
//        recyclerView.setAdapter(adapter);
//        tv_cs = (TextView) view.findViewById(R.id.tv_ceshi);
//        requestNetwork();
//        tv_cs.setText(jsonData);
        //doHttp();
        // String str = doHttp();

    }


//    private void initData()
//    {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run()
//            {
//
//            }
//        });
//
//    }


    private void requestNetwork()
    {
        final String newsUrl = NewsConfig.getNewsUrl(mType);//要请求的数据的网址
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                //初始化okHttpClient
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .get()
                        .url(newsUrl)
                        .build();
                //new 一个call对象，需要传入Request请求对象，然后执行这个请求
                //拿到返回给我们的Response 响应消息
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        jsonData = response.body().string();

                        List<NewsInfo> datas = jsonParse(jsonData);
                        //异步处理消息
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = datas;
                        handler.sendMessage(msg);

                        Log.d("琪露诺", "run: " + jsonData);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    //json解析
    private List<NewsInfo> jsonParse(String jsonData)
    {
        List<NewsInfo> newsDatas = new ArrayList<>();

        try {
            JSONObject object1 = new JSONObject(jsonData);
            if (object1.getString("reason").equals("成功的返回")) {
                JSONObject object2 = object1.getJSONObject("result");
                JSONArray jsonArray = object2.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    NewsInfo mData = new NewsInfo();
                    JSONObject o = jsonArray.getJSONObject(i);
                    mData.title = o.getString("title");
                    mData.author_name = o.getString("author_name");
                    mData.date = o.getString("date");
                    mData.category = o.getString("category");
                    mData.newsContentUrl = o.getString("url");
                    mData.picUrl = o.getString("thumbnail_pic_s");
                    newsDatas.add(mData);
                }
                return newsDatas;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*private void doHttp()
    {

        new Thread(new Runnable() {

            final String newsUrl = NewsConfig.getNewsUrl(mType);
            InputStream is = null;
            HttpURLConnection conn = null;
            @Override
            public void run()
            {
                try {
                    URL url = new URL(newsUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(5000);
                    conn.connect();
                    final StringBuffer sb ;
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        is = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        sb = new StringBuffer();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                tv_cs.setText(sb.toString());
                            }
                        });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    conn.disconnect();
                }
            }
        }).start();

        //   return null;
    }*/


}
