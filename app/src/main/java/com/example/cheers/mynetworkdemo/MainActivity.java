package com.example.cheers.mynetworkdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cheers.networklib.NetState;
import com.example.cheers.networklib.NetworkManager;
import com.example.cheers.networklib.annotation.Network;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        NetworkManager.getInstance().register(this);
         findViewById(R.id.tv_activity).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(context, Main2Activity.class));
             }
         });
    }

    // 监听全部网络
    @Network
    public void checkNet(NetState state){
        Log.i(TAG, "checkNet: ----- " + state);
    }

   // 只监听Wi-Fi
    @Network(value = NetState.NET_WIFI)
    public void checkWifiNet(NetState state){
        Log.i(TAG, "checkWifiNet: ----- " + state);
    }

    // 只监听数据流量
    @Network(value = NetState.NET_GPRS)
    public void checkGPRSNet(NetState state){
        Log.i(TAG, "checkCmwapNet: ----- " + state);
    }

}
