package nio.channel;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {


    /**
     * 访问 http://localhost:8888/
     * Incoming connection from: /0:0:0:0:0:0:0:1:57086
     *
     * @throws Exception
     */
    @Test
    public void serverSocketChannel() throws Exception {

        int port = 8888; //端口号
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port)); //绑定
        serverSocketChannel.configureBlocking(false);//设置非阻塞模式

        ByteBuffer buffer = ByteBuffer.wrap("you are good man".getBytes());

        //监听有新链接传入
        while (true) {
            System.out.println("Waiting for connections");
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) { //没有链接传入
                System.out.println("null");
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from: " + socketChannel.socket().getRemoteSocketAddress());
                buffer.rewind(); //指针0
                socketChannel.write(buffer);
                socketChannel.close();
            }
        }
    }


    @Test
    public void socketChannel() throws Exception {
        //创建SocketChannel
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));

//        SocketChannel socketChanne2 = SocketChannel.open();
//        socketChanne2.connect(new InetSocketAddress("www.baidu.com", 80));

//        socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, Boolean.TRUE)
//                .setOption(StandardSocketOptions.TCP_NODELAY, Boolean.TRUE);
//        Boolean KEEPALIVE  = socketChannel.getOption(StandardSocketOptions.SO_KEEPALIVE);
//        Integer RCVBUF = socketChannel.getOption(StandardSocketOptions.SO_RCVBUF);

        //设置阻塞和非阻塞

        socketChannel.configureBlocking(false);

        //读操作
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        socketChannel.read(byteBuffer);
        socketChannel.close();
        System.out.println("read over");

    }
}
