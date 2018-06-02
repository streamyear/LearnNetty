package com.streamyear.netty2.nio;

/**
 * 使用非阻塞io的形式编写服务端
 */
public class TimeServer {
    private final static int DEFAULT_PORT = 9090;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                // 没有传入参数就用默认的短裤
            }
        }
        // 创建多路复用器
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }


}
