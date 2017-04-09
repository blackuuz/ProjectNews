package com.touhou.uuz.projectnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.touhou.uuz.projectnews.Adapter.FavoriteAdapter;
import com.touhou.uuz.projectnews.R;
import com.touhou.uuz.projectnews.base.BaseFragment;
import com.touhou.uuz.projectnews.config.NewsConfig;

/**
 * Created by UUZ on 2017/2/15.
 */

public class Fm_Favorite extends BaseFragment {

    private RecyclerView mrecyclerView;
    private View view;
    private FavoriteAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fm_favorite, null);
        initwidget();
        return view;
    }

    private void initwidget()
    {
        mrecyclerView = (RecyclerView) view.findViewById(R.id.recycler_favorite);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mrecyclerView.setLayoutManager(manager);
        adapter = new FavoriteAdapter();
        adapter.addDatasToAdapter(NewsConfig.newsInfos);
        mrecyclerView.setAdapter(adapter);
    }

}
