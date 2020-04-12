package orrin.study.netty.chapter04;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author orrin on 2020-03-02
 */
public class IdleStateTypeHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

            String stateType = "";

            switch (idleStateEvent.state()) {
                case ALL_IDLE:
                    stateType = "读写空闲";
                    break;
                case READER_IDLE:
                    stateType = "读空闲";
                    break;
                case WRITER_IDLE:
                    stateType = "写空闲";
                    break;
            }

            System.out.println(ctx.channel().remoteAddress() + " - 超时事件 ：" + stateType);
            ctx.channel().close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
