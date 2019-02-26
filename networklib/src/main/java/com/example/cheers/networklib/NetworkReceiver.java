package com.example.cheers.networklib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.cheers.networklib.annotation.Network;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class NetworkReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkReceiver";
    public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private Map<Object, List<SubscribeMethodBean>> mSubscribes;
    private NetState mLastState = NetState.NET_UNKNOWN;

    public NetworkReceiver() {
        mSubscribes = new HashMap<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (CONNECTIVITY_CHANGE_ACTION.equals(action)){
            NetState netState = NetWorkUtil.getNetStateCode(context);
            if (mLastState == netState){
                // 主要解决同一状态会回调多次问题
                return;
            }
            post(netState);
            mLastState = netState;
        }
    }

    /**
     * 注册
     * @param obj
     */
    public void register(Object obj) {
        if (mSubscribes == null){
            mSubscribes = new HashMap<>();
        }
        if (mSubscribes.containsKey(obj)){
            return;
        }

        Class<?> clazz = obj.getClass();
        List<SubscribeMethodBean> subscribeMethods = findSubscribeMethods(clazz);
        mSubscribes.put(obj, subscribeMethods);
    }

    private List<SubscribeMethodBean> findSubscribeMethods(Class<?> clazz) {
        ArrayList<SubscribeMethodBean> subscribeMethodBeans = new ArrayList<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Network annotation = method.getAnnotation(Network.class);
            if (annotation == null){
                continue;
            }

            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1){
                Log.i(TAG, "findSubscribeMethods: 参数只能有一个");
                continue;
            }

            // isAssignableFrom 判断NetState.class 能不能转换为 parameterTypes[0] 类型
            if (!parameterTypes[0].isAssignableFrom(NetState.class)){
                Log.i(TAG, "findSubscribeMethods: 参数类型必须为NetState类型");
                continue;
            }
            subscribeMethodBeans.add(new SubscribeMethodBean(method, parameterTypes[0], annotation.value()));
        }
        return subscribeMethodBeans;
    }

    /**
     * 取消注册
     */
    public void unregister(Object obj) {
        if (mSubscribes != null && mSubscribes.containsKey(obj)){
            mSubscribes.remove(obj);
        }
    }

    /**
     * 取消所有注册
     */
    public void unAllRegister() {
        if (mSubscribes != null){
            mSubscribes.clear();
        }
    }

    private void post(NetState netState){
        Set<Object> objectSets = mSubscribes.keySet();
        for(Object obj : objectSets){
            List<SubscribeMethodBean> methodBeans = mSubscribes.get(obj);
            if (methodBeans == null || methodBeans.size() == 0){
                continue;
            }
            for (SubscribeMethodBean methodBean : methodBeans){
                switch (methodBean.getAnnotationValue()){
                    // 监听所有网络
                    case NET_ALL:
                        invoke(obj, methodBean.getMethod(), netState);
                        break;
                     // 只监听Wi-Fi
                    case NET_WIFI:
                        if (netState == NetState.NET_WIFI || netState == NetState.NET_NO){
                            invoke(obj, methodBean.getMethod(), netState);
                        }
                        break;
                     //只监听数据流量
                    case NET_GPRS:
                        if (netState == NetState.NET_GPRS || netState == NetState.NET_NO){
                            invoke(obj, methodBean.getMethod(), netState);
                        }
                        break;
                    default:break;
                }

            }
        }
    }

    /**
     * 通过反射执行注解方法
     * @param obj 对象
     * @param method 注解方法
     * @param objs 参数
     */
    private void invoke(Object obj, Method method, Object ...objs){
        try {
            method.invoke(obj, objs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
