package com.myproject.rpc;

import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        RpcProxyClient rpcProxyClient=new RpcProxyClient();
        ISayHelloService iSayHelloService=rpcProxyClient.clientProxy(
                ISayHelloService.class,"localhost",8888);
        System.out.println(iSayHelloService.sayHello("Kevin"));
    }
}
