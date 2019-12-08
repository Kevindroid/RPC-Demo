package com.myproject.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class RpcProxyClient {

    /**
     * 动态代理
     * @param interfaceClass
     * @param host
     * @param port
     * @param <T>
     * @return
     */
    public <T> T clientProxy(final Class<T> interfaceClass,final String host,final int port){
      return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class<?>[]{interfaceClass},
                new RemoteInvocationHandler(host,port));
    }
}
