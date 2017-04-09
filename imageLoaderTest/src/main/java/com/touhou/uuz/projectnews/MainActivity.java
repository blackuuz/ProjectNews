package com.touhou.uuz.projectnews;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private ImageView iv;
    private ImageLoader imageLoader;
    private String url;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.imageView);
        btn = (Button) findViewById(R.id.button);

        //url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/" +
        //      "superman/img/logo/bd_logo1_31bdc765.png";
        url = "http://i1.hdslb.com/bfs/archive/bdcfb845abc946dd2ed418bce051b68b6abc915e.jpg";
        initPermission();
        imageLoader = new ImageLoader(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                bitmap = imageLoader.loadImage(url, l);
                iv.setImageBitmap(bitmap);
            }
        });


    }


    //设置权限
    private void initPermission()
    {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    private ImageLoader.OnLoadImageListener l = new ImageLoader.OnLoadImageListener() {
        @Override
        public void onImageLoadOK(String url, Bitmap bitmap)
        {
            iv.setImageBitmap(bitmap);
        }

        @Override
        public void onImageLoadError(String url)
        {
            Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
        }
    };
}
