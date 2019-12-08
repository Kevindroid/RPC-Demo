package com.myproject.rpc;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        ISayHelloService sayHelloService=new SayHelloServiceImpl();
        RpcProxyServer proxyServer=new RpcProxyServer();
        System.out.println("=============start server============");
        proxyServer.publisher(sayHelloService,8888);
    }
}
