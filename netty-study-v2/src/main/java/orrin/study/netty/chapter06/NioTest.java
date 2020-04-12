package orrin.study.netty.chapter06;

import java.nio.IntBuffer;

/**
 * @author orrin on 2020-03-14
 */
public class NioTest {
    public static void main(String[] args) {
        IntBuffer ib = IntBuffer.allocate(6);

        prin(ib);
        for (int i = 0; i < 5; i++) {
            ib.put(i);
            prin(ib);
        }
        ib.flip();
        prin(ib);
        while (ib.hasRemaining()) {
            prin(ib);
            System.out.println(ib.get());
        }
        prin(ib);

        //ib.clear();

        for (int i = 0; i < 5; i++) {
            ib.put(i);
            prin(ib);
        }

        while (ib.hasRemaining()) {
            prin(ib);
            System.out.println(ib.get());
        }

    }

    public static void prin(IntBuffer ib) {
        System.out.println("capacity:" + ib.capacity() + " ,limit:" + ib.limit() + " ,position:" + ib.position());
    }
}
