package com.touhou.uuz.projectnews;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private MyAdapter adapter;
    private List<JavaBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        mrecyclerView = (RecyclerView) findViewById(R.id.recycler_01);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecyclerView.setLayoutManager(manager);

        adapter = new MyAdapter();
        adapter.addDatas(datas);
        mrecyclerView.setAdapter(adapter);


        //initData();

    }

    private void initData()
    {
        datas = new ArrayList<>();
        for(int i = 0;i<20;i++)
        {
            JavaBean data = new JavaBean();
            data.bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            data.mtitle = "东方project"+i;
            datas.add(data);
        }
    }


}
