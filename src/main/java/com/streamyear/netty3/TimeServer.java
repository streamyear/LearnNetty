package com.streamyear.netty3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 使用netty来完成服务端的开发
 */
public class TimeServer {
	private final static int DEFAULT_PORT = 9090;
	public static void main(String[] args) throws InterruptedException {
		int port = DEFAULT_PORT;
		if (args != null && args.length > 0){
			try {
				port = Integer.parseInt(args[0]);
			}catch (NumberFormatException e){
				// 没有传入参数就用默认的短裤
			}
		}
		new TimeServer().bind(port);
	}

	private void bind(int port) throws InterruptedException {
		// 配置服务端的NIO线程组
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChildChanelHandler());

			// 绑定端口
			ChannelFuture f = server.bind(port).sync();
			// 等待服务器监听端口关闭
			f.channel().closeFuture().sync();
		}finally {
			// 释放线程组资源
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

	private class ChildChanelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel socketChannel) throws Exception {
			socketChannel.pipeline().addLast(new TimeServerHandler());
		}
	}
}
