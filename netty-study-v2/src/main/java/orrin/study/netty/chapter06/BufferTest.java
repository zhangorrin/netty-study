package orrin.study.netty.chapter06;

import io.netty.util.NettyRuntime;

import java.nio.ByteBuffer;

/**
 * @author orrin on 2020-03-17
 */
public class BufferTest {
    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(100);

        bb.putInt(1);
        bb.putChar('你');
        bb.putDouble(14.123123);

        bb.flip();

        /*System.out.println(bb.getInt());
        System.out.println(bb.getChar());
        System.out.println(bb.getDouble());*/

        while (bb.hasRemaining()) {
            System.out.println(bb.get());
        }

        System.out.println(NettyRuntime.availableProcessors());

    }
}
