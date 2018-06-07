package com.streamyear.netty7;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试MessagePack的api
 */
public class TestMessagePack {
    public static void main(String[] args) throws IOException {
        // 创建需要序列化的对象
        List<String> src = new ArrayList<>();
        src.add("StreamYear");
        src.add("Xiaoshitou");
        src.add("MessagePack");

        MessagePack msgpack = new MessagePack();
        // 序列化
        byte[] raw = msgpack.write(src);
        System.out.println("序列化之后的大小为: " + raw.length);
        // 反序列化
        List<String> distSrc = msgpack.read(raw, Templates.tList(Templates.TString));
        System.out.println(distSrc.get(0));
        System.out.println(distSrc.get(1));
        System.out.println(distSrc.get(2));
    }
}
