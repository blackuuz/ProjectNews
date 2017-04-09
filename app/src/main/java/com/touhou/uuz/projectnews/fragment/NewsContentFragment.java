package com.touhou.uuz.projectnews.fragment;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.touhou.uuz.projectnews.MainActivity;
import com.touhou.uuz.projectnews.R;
import com.touhou.uuz.projectnews.config.NewsConfig;
import com.touhou.uuz.projectnews.entity.NewsInfo;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.touhou.uuz.projectnews.R.id.item;
import static com.touhou.uuz.projectnews.R.id.position_text_view;
import static com.touhou.uuz.projectnews.R.menu.toolbar;


/**
 * Created by UUZ on 2017/2/22.
 */

public class NewsContentFragment extends Fragment {

    //   private View item ;
    private Toolbar mToolbar;
    private View view;
    private WebView mWebView;
    private String murl;
    private NewsInfo info;
    private String imageView,title;

   /// boolean b ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.layout_fragment_newscontents, null);

        initwidget();

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolBar);
        Log.d("琪露诺0", "onCreateView: " + getActivity() + item);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId()) {
                    case R.id.collection:
                        boolean b = false;
                        if (NewsConfig.newsInfos.size() > 0) {
                            for (NewsInfo data : NewsConfig.newsInfos) {
                                if (data.newsContentUrl.equals(info.newsContentUrl)) {
                                    Toast.makeText(getContext(), "重复的收藏", Toast.LENGTH_SHORT).show();
                                    if(b){
                                        b= !b;
                                    }
                                }
                            }

                        } else {
                            NewsConfig.newsInfos.add(info);
                            Toast.makeText(getContext(), "收藏完成", Toast.LENGTH_SHORT).show();

                        }

                        if (b) {
                            NewsConfig.newsInfos.add(info);
                            Toast.makeText(getContext(), "收藏成果", Toast.LENGTH_SHORT).show();
                        }
                        break;



                    case R.id.item3:
                        showShare();
                        break;
                }
                return true;
            }
        });


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        /// murl = getArguments().getString("newsContentUrl");
        info = (NewsInfo) getArguments().getParcelable("newsContentUrl");
        murl = info.newsContentUrl;
        imageView = info.picUrl;
        title = info.title;



    }
    private void showShare() {
        ShareSDK.initSDK(getContext());
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(title);
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(murl);
// text是分享文本，所有平台都需要这个字段
        oks.setText("———--琪露诺—————测试————————");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(getContext());
    }

    private void initwidget()
    {
//        mToolbar = (Toolbar) view.findViewById(R.id.fg_toolbar);
//        mToolbar.inflateMenu(R.menu.toolbar);


        mWebView = (WebView) view.findViewById(R.id.webview_newscontent);
        //设置webview是否支持java脚本语言
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置网易在当前的webView上显示而不是第三方浏览器
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl(murl);


    }


    private void collectionNews()
    {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId()) {
            case R.id.collection:
                // getFragmentManager().findFragmentById();


                Toast.makeText(getContext(), "已收藏", Toast.LENGTH_SHORT).show();
                break;


        }


        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        //  mToolbar.inflateMenu(R.menu.toolbar);
    }


    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
//    {
//
////        inflater.inflate(,menu);
//    }
}
