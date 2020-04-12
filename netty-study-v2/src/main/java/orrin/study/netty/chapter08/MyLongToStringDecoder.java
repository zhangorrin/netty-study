package orrin.study.netty.chapter08;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author orrin on 2020/4/12
 */
public class MyLongToStringDecoder extends MessageToMessageDecoder<Long> {

    @Override
    protected void decode(ChannelHandlerContext ctx, Long msg, List<Object> out) throws Exception {
        System.out.println("MyLongToStringDecoder decode invoked ");
        out.add(String.valueOf(msg));
    }
}
