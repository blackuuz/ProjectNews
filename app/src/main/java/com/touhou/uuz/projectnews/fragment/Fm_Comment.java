package com.touhou.uuz.projectnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.touhou.uuz.projectnews.R;
import com.touhou.uuz.projectnews.base.BaseFragment;

/**
 * Created by UUZ on 2017/2/15.
 */

public class Fm_Comment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fm_comment, null);
        return v;
    }
}
