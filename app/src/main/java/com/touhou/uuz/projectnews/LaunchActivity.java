package com.touhou.uuz.projectnews;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Button;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;
import com.touhou.uuz.projectnews.base.BaseActivity;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * 启动页面的activity
 */
public class LaunchActivity extends BaseActivity {
    @Bind(R.id.tetureview)

    TextureView textureView;
    @Bind(R.id.btn_signin_launch)
    Button btn_signin;
    @Bind(R.id.btn_signup_launch)
    Button btn_signup;

    private MediaPlayer player;
    // Platform qq = ShareSDK.getPlatform(QQ.NAME);

    private int pausePosition;

    public static final int FROM_ACT = 1;

    //MediaPlayer
    //1.播放什么---数据
    //2.到哪播放---SurfaceView(缺点：不支持 ：平移、旋转、缩放)、
    // TextureView
    @OnClick(R.id.btn_signin_launch)
    public void signin()
    {

        setPlat();
        longin();

    }

    @OnClick(R.id.btn_signup_launch)
    public void signup()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("1",FROM_ACT);
        startActivity(MainActivity.class,bundle);
        this.finish();
        // Toast.makeText(this, "注册成功___并没有……", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        textureView.setSurfaceTextureListener(l);

    }

    private TextureView.SurfaceTextureListener l = new TextureView.SurfaceTextureListener() {
        @Override//当surfacetexture可用时
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height)
        {
            try {
                player = new MediaPlayer();
                //拿到assets 下的数据资源，并返回一个AssetFileDescriptor 类型的对象
                AssetFileDescriptor afd = getAssets().openFd("welcome.mp4");
                FileDescriptor fd = afd.getFileDescriptor();
                player.setDataSource(fd, afd.getStartOffset(), afd.getLength());//设置要播放的数据
                Surface surface = new Surface(surfaceTexture);
                player.setSurface(surface);
                player.setLooping(true);//设置循环播放
                player.prepareAsync();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp)
                    {
                        player.start();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override//大小改变
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height)
        {

        }

        @Override//销毁时
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface)
        {
            return false;
        }

        @Override//升级时
        public void onSurfaceTextureUpdated(SurfaceTexture surface)
        {

        }
    };

    @Override
    protected void onStop()
    {
        super.onStop();
        if (player.isPlaying()) {
            player.pause();
            pausePosition = player.getCurrentPosition();
        }
    }


    @Override
    protected void onRestart()
    {
        super.onRestart();
        if (!player.isPlaying()) {
            player.seekTo(pausePosition);//调到某一个位置
            player.start();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();//释放资源
            player = null;
        }
    }

    //获取权限
    private void getPlat()
    {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        qq.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2)
            {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2)
            {
                // TODO Auto-generated method stub
                //输出所有授权信息
                arg0.getDb().exportData();
            }

            @Override
            public void onCancel(Platform arg0, int arg1)
            {
                // TODO Auto-generated method stub
            }
        });
//authorize与showUser单独调用一个即可
        qq.authorize();//单独授权,OnComplete返回的hashmap是空的
        qq.showUser(null);//授权并获取用户信息
//移除授权
//weibo.removeAccount(true);
    }

    private void setPlat()
    {

        Platform qq = ShareSDK.getPlatform(this, QQ.NAME);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        qq.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2)
            {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2)
            {
                // TODO Auto-generated method stub
                //输出所有授权信息
                arg0.getDb().exportData();
            }

            @Override
            public void onCancel(Platform arg0, int arg1)
            {
                // TODO Auto-generated method stub

            }
        });
        qq.showUser(null);//执行登录，登录后在回调里面获取用户资料
//weibo.showUser(“3189087725”);//获取账号为“3189087725”的资料
    }


    public void longin()
    {

        new Thread(new Runnable() {
            @Override
            public void run()
            {
                getPlat();
                authorize(ShareSDK.getPlatform(QQ.NAME));
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                String accessToken = qq.getDb().getToken(); // 获取授权token
                String openId = qq.getDb().getUserId(); // 获取用户在此平台的ID
                String nickname = qq.getDb().getUserName(); // 获取用户昵称
                String icon = qq.getDb().getUserIcon();
//部分没有封装的字段可以通过键值获取，例如下面微信的unionid字段
//        Platform wechat = ShareSDK.getPlatform(QQ.NAME);
//        String unionid = wechat.getDb().get("unionid");


                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                intent.putExtra("Name", nickname);

                // intent.putExtra("ID",openId);
                intent.putExtra("Icon", icon);
                startActivity(intent);
                Log.d("uuzz", "run: " + icon);
            }
        }).start();
        LaunchActivity.this.finish();
    }


    private void authorize(Platform plat)
    {
        if (plat == null) {
            getPlat();
            return;
        }
//判断指定平台是否已经完成授权
        if (plat.isAuthValid()) {
            String userId = plat.getDb().getUserId();
            if (userId != null) {
//                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
//                login(plat.getName(), userId, null);
                return;
            }
        }
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap)
            {


            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable)
            {

            }

            @Override
            public void onCancel(Platform platform, int i)
            {

            }
        });
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(true);
        //获取用户资料
        plat.showUser(null);
    }


}
