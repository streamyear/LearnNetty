package com.streamyear.netty5.customDelimiter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoClientHandler extends ChannelHandlerAdapter {
	private byte[] req;
	private int counter;

	public EchoClientHandler() {
		req = ("QUERY TIME ORDER" + "$_").getBytes();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf buf = null;
		for (int i = 0; i < 100; i++){
			buf = Unpooled.copiedBuffer(req);
			ctx.writeAndFlush(buf);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("Now is : " + body + " ; the counter is : " + ++counter);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
