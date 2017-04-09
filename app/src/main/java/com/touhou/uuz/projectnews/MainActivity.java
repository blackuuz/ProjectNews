package com.touhou.uuz.projectnews;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapFragment;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.squareup.picasso.Picasso;
import com.touhou.uuz.projectnews.base.BaseActivity;
import com.touhou.uuz.projectnews.fragment.Fm_Comment;
import com.touhou.uuz.projectnews.fragment.Fm_Favorite;
import com.touhou.uuz.projectnews.fragment.Fm_Local;
import com.touhou.uuz.projectnews.fragment.Fm_News;
import com.touhou.uuz.projectnews.fragment.Fm_Photo;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.touhou.uuz.projectnews.fragment.NewsContentFragment;
import com.touhou.uuz.projectnews.util.CameraAlbumUtil;
import com.touhou.uuz.projectnews.util.ImageLoadUtils;
import com.touhou.uuz.projectnews.util.PermissionUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    @Bind(R.id.toolBar)
    Toolbar toolbar;
    @Bind(R.id.activity_main)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav)
    NavigationView nav;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    // private MapFragment mapFragment;
    private CircleImageView iv_headPic;
    //private Uri headPicUri;
    private CameraAlbumUtil cameraAlbumUtil;
    private ImageLoadUtils img;
    private TextView tv_name;


    //DrawerLayout 抽屉布局
    //NavigationView :侧滑菜单  ---menu + headerLayout
    //public LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);//初始化ButterKnife,  将视图和空间进行绑定
        cameraAlbumUtil = new CameraAlbumUtil(this);
        img = new ImageLoadUtils(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initToolBar();
        initNavigationView();
        initHeaderLayout();


        setHead();
        // cutImageByAlbumIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item:
                Toast.makeText(this, "item", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item3:
                startActivity(Share.class);
                Toast.makeText(this, "item3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.collection:
                //Toast.makeText(this, "Collection", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
        return true;
    }

    public void changeMenuClick(Fragment fragment)
    {
        fm = getSupportFragmentManager();
        FragmentTransaction ft = transaction = fm.beginTransaction();
        //传入的fragment是newscontentFragment 就将其加入返回栈
        if (fragment instanceof NewsContentFragment) {
            ft.addToBackStack(null);
        }

        transaction.replace(R.id.fl_main_t, fragment);
        transaction.commit();
    }

    //初始化NavigationView的头布局HeaderView
    private void initHeaderLayout()
    {
        //1.利用NavigationView对象找到它的头布局
        View headerLayout = nav.getHeaderView(0);
        //2.在头布局内找对应控件
        iv_headPic = (CircleImageView) headerLayout.findViewById(R.id.circleImageView);
        tv_name = (TextView) headerLayout.findViewById(R.id.tv_tv1);

        iv_headPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                chooseDialog();
            }
        });
    }


    private void chooseDialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("选择头像")
                .setPositiveButton("相机", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        PermissionUtil.requestPermissions(MainActivity.this, 111, new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtil.OnRequestPermissionListener() {
                            @Override
                            public void onRequestGranted()
                            {
                                cameraAlbumUtil.takePhoto();
                            }

                            @Override
                            public void onRequestDenied()
                            {
                                Toast.makeText(MainActivity.this, "权限被拒绝", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })

                .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        PermissionUtil.requestPermissions(MainActivity.this, 111, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtil.OnRequestPermissionListener() {
                            @Override
                            public void onRequestGranted()
                            {
                                cameraAlbumUtil.openAlbum();
                            }

                            @Override
                            public void onRequestDenied()
                            {
                                Toast.makeText(MainActivity.this, "权限被拒绝", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Bitmap bitmap = CameraAlbumUtil.onActivityResult(requestCode, resultCode, data);
        if (bitmap != null) {
            iv_headPic.setImageBitmap(bitmap);
        }

    }


    private void initToolBar()
    {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
    }


    private void initNavigationView()
    {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId()) {
                    case R.id.news:
                        changeMenuClick(new Fm_News());
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.favorite:
                        changeMenuClick(new Fm_Favorite());
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.local:
                        changeMenuClick(new Fm_Local());
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.comment:
                        changeMenuClick(new Fm_Comment());
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.photo:
                        changeMenuClick(new Fm_Photo());
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
    }


    private void setHead()
    {
        Bundle bundle = getIntent().getExtras();
        int a = bundle.getInt("1");
        if(a == LaunchActivity.FROM_ACT){
            return;
        }
        String name = getIntent().getStringExtra("Name");
        //String ID = getIntent().getStringExtra("ID");

        tv_name.setText(name);
        //tv_numb.setText(ID);
        String icon = getIntent().getStringExtra("Icon");


        Picasso.with(this).load(icon)
                .fit()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ssdk_oks_classic_qq)
                .into(iv_headPic);

    }
}



