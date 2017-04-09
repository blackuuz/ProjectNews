package com.touhou.uuz.projectnews.fragment;

import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.touhou.uuz.projectnews.MainActivity;
import com.touhou.uuz.projectnews.R;
import com.touhou.uuz.projectnews.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by UUZ on 2017/2/15.
 */

public class Fm_Local extends BaseFragment implements View.OnClickListener {

    private StringBuffer sb = new StringBuffer(1024);
    private TextView tv;
    private Button btn_1, btn_2;
    private MapView mMapView = null;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    public LocationClient mLocationClient = null;


    public BDLocationListener myListener = new MyLocationListener();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fm_local, null);

        btn_1 = (Button) v.findViewById(R.id.btn_fm_l);
        btn_2 = (Button) v.findViewById(R.id.btn_fm_l_2);
        mMapView = (MapView) v.findViewById(R.id.bd_map);
        tv = (TextView) v.findViewById(R.id.tv_local);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        return v;

    }

    private void navigateTo(BDLocation location)
    {
        if (isFirstLocate) {
            Log.d("幽幽子", "onReceiveLocati"+location.getLatitude()+"-----" +location.getLongitude());
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomBy(13);
            baiduMap.animateMapStatus(update);
            Log.d("幽幽子", location.getLatitude()+"-----" +location.getLongitude());

            isFirstLocate = false;

        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLatitude());
        MyLocationData locationData = builder.build();
        baiduMap.setMyLocationData(locationData);
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        //mLocationClient = new LocationClient(getContext());
        SDKInitializer.initialize(context.getApplicationContext());
        mLocationClient = new LocationClient(context.getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数


        initPermission();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getContext(), "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(getContext(), "发生未知错误", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mMapView.onResume();
    }


    @Override
    public void onPause()
    {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mLocationClient.stop();
        mMapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.btn_fm_l:
                mLocationClient.start();
                Toast.makeText(getContext(), "开启定位", Toast.LENGTH_SHORT).show();
                tv.setText(sb.toString());

                break;
            case R.id.btn_fm_l_2:
                mLocationClient.stop();
                Toast.makeText(getContext(), "停止定位", Toast.LENGTH_SHORT).show();
                sb.delete(0, sb.length());
                tv.setText("");
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location)
        {
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(location);

            }

            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\n纬度 : ");
            sb.append(location.getLatitude());    //获取纬度信息

            sb.append("\n经度 : ");
            sb.append(location.getLongitude());    //获取经度信息

            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度

            if (location.getLocType() == BDLocation.TypeGpsLocation) {

                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时

                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数

                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米

                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度

                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息

                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");

            } else if (location.getLocType() == BDLocation.TypeServerError) {

                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息

            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
                    tv.setText(sb);
                    Log.i("Baidu测试", sb.toString());
                }
            });

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i)
        {

        }
    }


    //设置权限
    private void initPermission()
    {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        } else {
            requestLocation();
        }
    }

    private void requestLocation()
    {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation()
    {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

}
