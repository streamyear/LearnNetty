package com.streamyear.netty2.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 同步的io的服务端
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
			while (true){
				socket = serverSocket.accept();
				new Thread(new TimeServerHandler(socket)).start();
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
