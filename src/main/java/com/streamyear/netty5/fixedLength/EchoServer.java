package com.streamyear.netty5.fixedLength;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 使用固定长度的解码器解决粘包和拆包的问题
 * DelimiterBasedFrameDecoder的应用
 */
public class EchoServer {
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
		new EchoServer().bind(port);
	}

	private void bind(int port) throws InterruptedException {
		// 配置服务端的NIO线程组
		/**
		 * 两个线程组的作用:
		 * 1、用于服务端接收客户端的连接
		 * 2、用户进行SocketChannel的网络读写
		 */
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel sc) throws Exception {
							sc.pipeline().addLast(new FixedLengthFrameDecoder(20))
									.addLast(new StringDecoder())
									.addLast(new EchoServerHandler());
						}
					});

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

}
