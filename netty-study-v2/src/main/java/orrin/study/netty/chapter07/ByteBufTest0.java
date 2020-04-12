package orrin.study.netty.chapter07;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author orrin on 2020-04-07
 */
public class ByteBufTest0 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10);

        for (int i = 0; i < byteBuf.capacity(); i++) {
            byteBuf.writeByte(i);
        }


        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.getByte(i));
        }

        while (byteBuf.isReadable()) {
            System.out.println(byteBuf.readByte());
        }

    }
}
