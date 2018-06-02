package com.streamyear.netty2.bogusIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServerHandler implements Runnable {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Socket socket;

	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;

		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(), true);
			String currentTime = null;
			String body = null;

			while (true){
				body = in.readLine();
				if (body == null){
					break;
				}
				System.out.println("The time server receive order : " + body);
				currentTime = "QUERY TIME ORDER".equals(body) ?
						sdf.format(new Date(System.currentTimeMillis())) : "BAD ORDER";
				out.println(currentTime);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null){
				out.close();
			}
			if (this.socket != null){
				try {
					this.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.socket = null;
			}
		}

	}
}
