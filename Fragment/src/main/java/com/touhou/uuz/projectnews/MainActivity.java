package com.touhou.uuz.projectnews;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
private FragmentManager fm;
    private FragmentTransaction transaction;
    public final String TAG ="琪露诺";
    private String msg = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: "+"MainActivity");
        fm =  getSupportFragmentManager();//v4包那fragment 的方法
        transaction = fm.beginTransaction();//开启一个事物
        //第一个参数是容器的id，第二参数是将要被添加activity上的fragment对象
        transaction.add(R.id.framelayout_,new FragmentA());
        transaction.commit();//提交一个事物

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart: "+msg);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume: "+msg);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause: "+msg);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        Log.d(TAG, "onStop: "+msg);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy: "+msg);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d(TAG, "onRestart: "+msg);
    }
}
