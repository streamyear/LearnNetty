package com.streamyear.netty7.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * 使用MessagePack完成编码
 */
public class MsgEncoder extends MessageToByteEncoder<Object> {
	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
		MessagePack pack = new MessagePack();
		// 开始序列化
		byte[] raw = pack.write(o);
		byteBuf.writeBytes(raw);
	}
}
