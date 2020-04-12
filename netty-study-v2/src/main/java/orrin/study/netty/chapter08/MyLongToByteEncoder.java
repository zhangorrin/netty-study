package orrin.study.netty.chapter08;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author orrin on 2020/4/11
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncode encode invoked");

        System.out.println("msg = " + msg);
        if (msg != null) {
            out.writeLong(msg);
        }
    }
}
