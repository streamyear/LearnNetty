package com.streamyear.netty2.aio;

/**
 * 异步io(AIO)实现服务端
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
        AsyncTimeServerHandler asyncTimeServerHandler = new AsyncTimeServerHandler(port);
        new Thread(asyncTimeServerHandler).start();
    }
}
