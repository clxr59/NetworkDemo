package com.example.cheers.mynetworkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.cheers.networklib.NetState;
import com.example.cheers.networklib.NetworkManager;
import com.example.cheers.networklib.annotation.Network;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        NetworkManager.getInstance().init(this);
        NetworkManager.getInstance().register(this);
    }


    // 监听全部网络
    @Network
    public void checkNet(NetState state){
        Log.i(TAG, "checkNet2: ----- " + state);
    }

}
