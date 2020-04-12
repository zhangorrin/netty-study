package orrin.study.netty.chapter07;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

/**
 * @author orrin on 2020-04-08
 * Netty ByteBuf所提供的3种缓冲区类型
 * heap buffer、direct buffer、composite buffer
 */
public class ByteBufTest2 {
    public static void main(String[] args) {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();

        ByteBuf heapBuffer = Unpooled.buffer(10);
        ByteBuf directBuffer = Unpooled.directBuffer(8);

        compositeByteBuf.addComponents(heapBuffer, directBuffer);

        //compositeByteBuf.removeComponent(0);

        Iterator<ByteBuf> iterator = compositeByteBuf.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        compositeByteBuf.forEach(System.out::println);

    }
}
