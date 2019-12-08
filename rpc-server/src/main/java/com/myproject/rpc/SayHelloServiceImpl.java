package com.myproject.rpc;

public class SayHelloServiceImpl implements ISayHelloService {
    @Override
    public String sayHello(String txt) {
        return "Hello First RPC Demo: "+txt;
    }
}
