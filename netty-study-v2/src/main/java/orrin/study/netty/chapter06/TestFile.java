package orrin.study.netty.chapter06;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author orrin on 2020-03-16
 */
public class TestFile {
    public static void main(String[] args) throws Exception {
        io();
        nio();
        nioDirect();
        nio2();
    }

    private static void io() throws IOException {
        long start = System.currentTimeMillis();

        InputStream inputStream = new BufferedInputStream(new FileInputStream("/Users/orrin/Downloads/coeus-eye.2020-03-02.log"));

        byte[] buffer = new byte[1024];

        int bytesRead = 0;

        OutputStream out = new BufferedOutputStream(new FileOutputStream("/Users/orrin/Downloads/coeus-eye.2020-03-02.io.log"));

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String str = new String(buffer, 0, bytesRead, "GBK");
                out.write(str.getBytes());
            }
            out.flush();
        } finally {
            inputStream.close();
            out.close();
        }

        System.out.println("io time: " + (System.currentTimeMillis() - start));
    }

    private static void nio() throws Exception {
        long start = System.currentTimeMillis();

        FileInputStream inputStream = new FileInputStream("/Users/orrin/Downloads/coeus-eye.2020-03-02.log");

        FileOutputStream outputStream = new FileOutputStream("/Users/orrin/Downloads/coeus-eye.2020-03-02.nio.log");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true) {
            byteBuffer.clear();
            int read = inputChannel.read(byteBuffer);
            if (read == -1) {
                break;
            }
            byteBuffer.flip();
            outputChannel.write(byteBuffer);
        }


        System.out.println("nio time: " + (System.currentTimeMillis() - start));
    }

    private static void nioDirect() throws Exception {
        long start = System.currentTimeMillis();

        FileInputStream inputStream = new FileInputStream("/Users/orrin/Downloads/coeus-eye.2020-03-02.log");

        FileOutputStream outputStream = new FileOutputStream("/Users/orrin/Downloads/coeus-eye.2020-03-02.nioDirect.log");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        while (true) {
            byteBuffer.clear();
            int read = inputChannel.read(byteBuffer);
            if (read == -1) {
                break;
            }
            byteBuffer.flip();
            outputChannel.write(byteBuffer);
        }


        System.out.println("nioDirect() time: " + (System.currentTimeMillis() - start));
    }

    private static void nio2() throws Exception {
        long start = System.currentTimeMillis();

        FileInputStream inputStream = new FileInputStream("/Users/orrin/Downloads/coeus-eye.2020-03-02.log");

        FileOutputStream outputStream = new FileOutputStream("/Users/orrin/Downloads/coeus-eye.2020-03-02.nio2.log");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        long count = inputChannel.transferTo(0, inputChannel.size(), outputChannel);


        System.out.println("nio2() time: " + (System.currentTimeMillis() - start));
    }
}
