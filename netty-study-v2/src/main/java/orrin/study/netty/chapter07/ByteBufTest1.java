package orrin.study.netty.chapter07;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @author orrin on 2020-04-07
 */
public class ByteBufTest1 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world方法", Charset.forName("utf-8"));


        // 堆上缓存 返回true，直接缓存返回false
        if(byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            System.out.println(new String(content, Charset.forName("utf-8")));

            System.out.println(byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            System.out.println(byteBuf.readableBytes());

            for (int i=0;i<byteBuf.readableBytes();i++) {
                System.out.println((char)byteBuf.getByte(i));
            }
            while (byteBuf.isReadable()) {
                System.out.println(byteBuf.readByte());
            }

            System.out.println(byteBuf.getCharSequence(11, 6, Charset.forName("utf-8")));
        }
    }
}
