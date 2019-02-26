package com.example.cheers.networklib;

import java.lang.reflect.Method;
import java.time.Month;

/**
 * 注册的方法bean
 */
public class SubscribeMethodBean {
    /**
     * 注解的方法
     */
    private Method method;

    /**
     * 方法的参数
     */
    private Class clazz;

    /**
     * 注解的值
     */
    private NetState annotationValue;

    public SubscribeMethodBean(Method method, Class clazz, NetState netState) {
        this.method = method;
        this.clazz = clazz;
        this.annotationValue = netState;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public NetState getAnnotationValue() {
        return annotationValue;
    }

    public void setAnnotationValue(NetState annotationValue) {
        this.annotationValue = annotationValue;
    }
}
