package orrin.study.netty.chapter03;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author orrin on 2020-03-02
 */
public class ChatClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup clientGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup).channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();

            //channelFuture.channel().closeFuture().sync();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                channelFuture.channel().writeAndFlush(br.readLine() + "\r\n");
            }

        } finally {
            clientGroup.shutdownGracefully();
        }
    }
}
