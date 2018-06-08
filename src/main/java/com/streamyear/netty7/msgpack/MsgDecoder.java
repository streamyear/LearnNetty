package com.streamyear.netty7.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * 使用MessagePack完成解码
 */
public class MsgDecoder extends MessageToMessageDecoder<ByteBuf> {
	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		byte[] array = new byte[byteBuf.readableBytes()];
		byteBuf.getBytes(byteBuf.readerIndex(), array, 0, array.length);

		MessagePack messagePack = new MessagePack();
		// 反序列化
		list.add(messagePack.read(array));
	}
}
