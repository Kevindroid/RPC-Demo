package com.myproject.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.Provider;

public class RpcNetTransport {

    private String host;
    private int port;

    public RpcNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Socket newSocket() throws IOException {
        System.out.println("开始构建一个客户端连接");
        Socket socket = new Socket(host, port);
        return socket;
    }

    public Object send(RpcRequest request) {
        Socket socket = null;
        Object result=null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        try {
            //构建客户端请求，把request写入到服务器
            socket = newSocket();
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();
            //截止目前服务端收到rpc请求
            inputStream = new ObjectInputStream(socket.getInputStream());//得到服务端的响应结果
            result=inputStream.readObject();
        } catch (Exception e) {
            throw e;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }
}
