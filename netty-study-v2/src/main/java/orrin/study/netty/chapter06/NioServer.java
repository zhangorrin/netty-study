package orrin.study.netty.chapter06;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author orrin on 2020-03-20
 */
public class NioServer {

    public static final Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                selectionKeys.forEach(selectionKey -> {
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        try {
                            SocketChannel client = server.accept();
                            client.configureBlocking(false);

                            client.register(selector, SelectionKey.OP_READ);

                            String key = "[" + UUID.randomUUID().toString() + "]";
                            clientMap.put(key, client);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else if (selectionKey.isReadable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteFromClient = ByteBuffer.allocate(1024);
                        try {
                            int read = client.read(byteFromClient);

                            if (read > 0) {
                                byteFromClient.flip();

                                Charset charset = Charset.forName("UTF-8");

                                String msgFromClient = String.valueOf(charset.decode(byteFromClient).array());
                                System.out.println(client + " : " + msgFromClient);

                                String clientKey = "";
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    if (entry.getValue() == client) {
                                        clientKey = entry.getKey();
                                        break;
                                    }
                                }
                                ByteBuffer byteFromServer = ByteBuffer.allocate(1024);
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    byteFromServer.clear();
                                    byteFromServer.put((clientKey + " : " + msgFromClient).getBytes());
                                    byteFromServer.flip();
                                    entry.getValue().write(byteFromServer);
                                }

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                selectionKeys.clear();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
