package orrin.study.netty.chapter06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author orrin on 2020-03-20
 */
public class NioClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 8899));

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        while (true) {
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            selectionKeys.forEach(selectionKey -> {
                if (selectionKey.isConnectable()) {
                    try {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        if (client.isConnectionPending()) {
                            client.finishConnect();
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((LocalDateTime.now() + " 连接成功").getBytes(Charset.forName("UTF-8")));
                            writeBuffer.flip();
                            client.write(writeBuffer);

                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() -> {
                                while (true) {
                                    try {
                                        writeBuffer.clear();

                                        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                        String clientInputMsg = bufferedReader.readLine();
                                        writeBuffer.put(clientInputMsg.getBytes());
                                        writeBuffer.flip();
                                        client.write(writeBuffer);

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });

                        }
                        client.register(selector, SelectionKey.OP_READ);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (selectionKey.isReadable()) {

                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteMsgFromServer = ByteBuffer.allocate(1024);
                    try {
                        int count = client.read(byteMsgFromServer);
                        if (count > 0) {
                            byteMsgFromServer.flip();

                            Charset charset = Charset.forName("UTF-8");

                            String msgFromServer = String.valueOf(charset.decode(byteMsgFromServer).array());
                            System.out.println("server: " + msgFromServer);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            selectionKeys.clear();
        }

    }
}
