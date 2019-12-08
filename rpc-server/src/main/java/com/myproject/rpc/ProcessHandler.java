package com.myproject.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessHandler implements Runnable {

    Socket socket;
    Object service;

    public ProcessHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        //当有IO数据过来时候会执行此方法
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            //inputStream里面应该存在什么信息？
            //请求方法参数
            //请求方法名称
            //请求目标类
            //请求参数类型

            //反序列化
            RpcRequest rpcRequest=(RpcRequest) inputStream.readObject();
            Object result=invoke(rpcRequest);
            outputStream=new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);//序列化，写入到通信管道上
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }if (outputStream!=null){
                try {
                    outputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    private Object invoke(RpcRequest rpcRequest) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] args=rpcRequest.getParams();
        Class<?>[] types=new Class[args.length];
        for (int i=0;i<args.length;i++){
            types[i]=args[i].getClass();
        }
        //反射加载对应class
        Class clazz=Class.forName(rpcRequest.getClassName());
        //通过反射找到对应class中的方法
        Method method=clazz.getMethod(rpcRequest.getMethodName(),types);
        Object result=method.invoke(service,args);
        return result;
    }
}
