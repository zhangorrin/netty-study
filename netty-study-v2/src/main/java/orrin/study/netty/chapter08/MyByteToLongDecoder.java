package orrin.study.netty.chapter08;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author orrin on 2020/4/11
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecode decode invoked");

        System.out.println(in.readableBytes());

        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
