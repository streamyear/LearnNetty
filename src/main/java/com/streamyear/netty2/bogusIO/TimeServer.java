package com.streamyear.netty2.bogusIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪异步通信的服务端：服务端使用线程池来处理客户端的请求
 */
public class TimeServer {
	private final static int DEFAULT_PORT = 9090;

	public static void main(String[] args) throws IOException {
		int port = DEFAULT_PORT;
		if (args != null && args.length > 0){
			try {
				port = Integer.parseInt(args[0]);
			}catch (NumberFormatException e){
				// 没有传入参数就用默认的短裤
			}
		}
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("The time server is start in port : " + port);
			Socket socket = null;
			// 使用线程池来处理客户端的请求
            TimeServerHandlerExecutePool threadPool = new TimeServerHandlerExecutePool(50, 1000);
            while (true){
				socket = serverSocket.accept();
                threadPool.execute(new TimeServerHandler(socket));
			}
		}finally {
			if (serverSocket != null){
				System.out.println("The time server close.");
				serverSocket.close();
				serverSocket = null;
			}
		}
	}
}
