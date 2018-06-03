package com.streamyear.netty2.nio;

/**
 * 使用非阻塞的io(NIO)来实现客户端
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 9090;
        String ip = "127.0.0.1";
        if (args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                // 没有传入参数就用默认的短裤
            }
        }
        TimeClientHandle timeClient = new TimeClientHandle(ip, port);
        new Thread(timeClient).start();
    }
}
