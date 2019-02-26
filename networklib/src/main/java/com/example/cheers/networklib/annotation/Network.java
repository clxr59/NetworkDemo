package com.example.cheers.networklib.annotation;

import com.example.cheers.networklib.NetState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Network {
    NetState value() default NetState.NET_ALL;
}
