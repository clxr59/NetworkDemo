package com.example.cheers.mynetworkdemo;

import android.app.Application;

import com.example.cheers.networklib.NetworkManager;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getInstance().init(this);
    }
}
