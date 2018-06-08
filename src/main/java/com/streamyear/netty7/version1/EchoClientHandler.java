package com.streamyear.netty7.version1;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoClientHandler extends ChannelHandlerAdapter {
	private int sendNumber;

	public EchoClientHandler(int sendNumber) {
		this.sendNumber = sendNumber;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		UserInfo[] infos = getUserInfoArr();
		for (UserInfo info : infos) {
			ctx.write(info);
		}
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Client receive the msgpack message : " + msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	private UserInfo[] getUserInfoArr(){
		UserInfo[] result = new UserInfo[sendNumber];
		UserInfo userInfo = null;
		for (int i = 0; i < sendNumber; i++){
			userInfo = new UserInfo();
			userInfo.setAge(i);
			userInfo.setName("ABCDEF --> " + i);
			result[i] = userInfo;
		}
		return result;
	}
}
