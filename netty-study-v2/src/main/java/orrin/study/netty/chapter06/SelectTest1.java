package orrin.study.netty.chapter06;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author orrin on 2020-03-20
 */
public class SelectTest1 {
    public static void main(String[] args) throws Exception {
        int[] ports = new int[5];
        ports[0] = 8000;
        ports[1] = 8001;
        ports[2] = 8002;
        ports[3] = 8003;
        ports[4] = 8004;

        Selector selector = Selector.open();

        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocketChannel.socket().bind(address);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("开始监听端口: " + ports[i]);
        }

        while (true) {
            int numbers = selector.select();
            System.out.println("numbers: " + numbers);

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();

                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);

                    iterator.remove();

                    System.out.println("获取客户端连接：" + socketChannel);
                } else if (selectionKey.isReadable()) {

                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                    int readByte = 0;

                    while (true) {
                        byteBuffer.clear();
                        int read = socketChannel.read(byteBuffer);
                        readByte += read;
                        if (read <= 0) {
                            break;
                        }
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                    }
                    System.out.println("读取：" + readByte + ", 来自于：" + socketChannel);

                    iterator.remove();

                }

            }
        }


    }
}
