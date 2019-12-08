package com.myproject.rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcProxyServer {

    private final ExecutorService executorService= Executors.newCachedThreadPool();

    /**
     *
     * @param service 发布出去的服务
     * @param port 暴露的端口号
     *
     */
    public void publisher(Object service,int port) throws IOException {
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(port);
            while (true){
               final Socket socket= serverSocket.accept();//获得一个远程连接(默认阻塞状态）
                //如果进入到这个步骤说明有客户端连接过来
                //socket.getInputStream();//阻塞IO
                executorService.execute(new ProcessHandler(socket,service));
            }
        }catch (Exception e){

        }finally {
            if (serverSocket!=null){
                serverSocket.close();
            }
        }
    }
}
