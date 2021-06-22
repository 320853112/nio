package base.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class SendClient {

    private Charset charset = Charset.forName("UTF-8");

    public void sendFile(){
        try{
            String sourcePath = "";
            String destPath = "";
            File file = new File(sourcePath);
            if (!file.exists()){
                System.out.println("文件不存在");
                return;
            }
            FileChannel fileChannel = new FileOutputStream(file).getChannel();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.socket().connect(new InetSocketAddress("127.0.0.1", 18899));
            socketChannel.configureBlocking(false);
            System.out.println("Client 成功连接客户端");

            while (!socketChannel.finishConnect()){

            }
            ByteBuffer buffer = sendFileNameAndLength(destPath, file, socketChannel);

            int length = sendContent(file, fileChannel, socketChannel, buffer);
            if (length == -1){
                fileChannel.close();
                socketChannel.shutdownOutput();
                socketChannel.close();
            }
            System.out.println("文件传输成功");
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    private int sendContent(File file, FileChannel fileChannel, SocketChannel socketChannel, ByteBuffer buffer) throws IOException {
        System.out.println("开始传输文件");
        int length = 0;
        long progress = 0;
        while ((length = fileChannel.read(buffer)) >0){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
            progress += length;
            System.out.println("| "+(100*progress/file.length())+"%|");
        }
        return length;
    }

    private ByteBuffer sendFileNameAndLength(String destPath, File file, SocketChannel socketChannel) throws IOException{
        ByteBuffer fileNameByteBuffer = charset.encode(destPath);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //发送文件名称长度
        int fileNameLen = fileNameByteBuffer.capacity();
        buffer.putInt(fileNameLen);
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
        System.out.println("Client 文件名称长度发送完成：" + fileNameLen );

        //发送文件名称
        socketChannel.write(fileNameByteBuffer);
        System.out.println("文件名称发送完成" + destPath);
        //发送文件长度
        buffer.putLong(file.length());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
        System.out.println("Client 文件长度发送完成" + file.length());
        return buffer;

    }


}
