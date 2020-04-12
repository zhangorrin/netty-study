package orrin.study.netty.chapter08;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author orrin on 2020-03-01
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("Client# recieve " + msg + " from server(" + ctx.channel().remoteAddress() + ")");
        //ctx.channel().writeAndFlush(System.currentTimeMillis());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(System.currentTimeMillis());
        ctx.writeAndFlush(1L);
        ctx.writeAndFlush(2L);
        ctx.writeAndFlush(3L);
        ctx.writeAndFlush(4L);
        ctx.writeAndFlush(5L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
