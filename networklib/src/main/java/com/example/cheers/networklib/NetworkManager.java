package com.example.cheers.networklib;

import android.content.Context;
import android.content.IntentFilter;

public class NetworkManager {
    private static NetworkManager manager;
    private NetworkReceiver mReceiver;

    public static NetworkManager getInstance() {
        if (null == manager){
            synchronized (NetworkManager.class){
                if (null == manager){
                    manager = new NetworkManager();
                }
            }
        }
        return manager;
    }

    private NetworkManager(){

    }


    public void init(Context context){
        mReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(NetworkReceiver.CONNECTIVITY_CHANGE_ACTION);
        context.registerReceiver(mReceiver, filter);
    }

    public void register(Object object){
        mReceiver.register(object);
    }

    public void unregister(Object obj){
        mReceiver.unregister(obj);
    }

    public void unAllRegister(){
        mReceiver.unAllRegister();
    }

}
