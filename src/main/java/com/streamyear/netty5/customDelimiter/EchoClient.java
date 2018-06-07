package com.streamyear.netty5.customDelimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 自定义分隔符解决粘包和拆包的问题
 * DelimiterBasedFrameDecoder的应用
 */
public class EchoClient {
	public static void main(String[] args) throws InterruptedException {
		int port = 9090;
		String ip = "127.0.0.1";
		if (args != null && args.length > 0){
			try {
				port = Integer.parseInt(args[0]);
			}catch (NumberFormatException e){
				// 没有传入参数就用默认的短裤
			}
		}
		new EchoClient().connect(ip, port);
	}

	private void connect(String ip, int port) throws InterruptedException {
		// 配置客户端的线程组
		NioEventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap client = new Bootstrap();
			client.group(group)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							// 自定义分割符号
							ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
							socketChannel.pipeline()
									.addLast(new DelimiterBasedFrameDecoder(1024, delimiter))
									.addLast(new StringDecoder())
									.addLast(new EchoClientHandler());
						}
					});

			// 发起异步连接
			ChannelFuture f = client.connect(ip, port).sync();
			// 等待客户端链路关闭
			f.channel().closeFuture().sync();

		}finally {
			group.shutdownGracefully();
		}
	}
}
