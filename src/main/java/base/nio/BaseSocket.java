package base.nio;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class BaseSocket {

    public static void main(String[] args) throws Exception{
        connect();
    }

    public static void connect() throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 80));

    }


}
