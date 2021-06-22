package base.nio;


import sun.nio.ch.IOUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BaseChannel {

    public static void main(String[] args) {
        nioCopyResourceFile();
    }


    public static void nioCopyResourceFile(){
        String sourcePath = "C:\\Users\\xuhang01\\Desktop\\文件\\137-785544699-330100-000000-1623931362-00002.zip";
        String destPath = "C:\\Users\\xuhang01\\Desktop\\文件\\a.zip";
        nioCopyFile(sourcePath, destPath);

    }

    private static void nioCopyFile(String sourcePath, String destPath) {
        File srcFile = new File(sourcePath);
        File destFile = new File(destPath);
        try{
            if (!destFile.exists()){
                destFile.createNewFile();
            }
            long startTime = System.currentTimeMillis();
            FileInputStream fis = null;
            FileOutputStream fos = null;
            FileChannel inChannel = null;
            FileChannel outChannel = null;
            try{
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(destPath);
                inChannel = fis.getChannel();
                outChannel = fos.getChannel();
                int length = -1;
                ByteBuffer buf = ByteBuffer.allocate(1024);
                while ((length = inChannel.read(buf)) !=-1){
                    buf.flip();
                    int outLength = 0;
                    while((outLength = outChannel.write(buf)) != 0){
                        System.out.println("写入的字节数" + outLength);
                    }
                    buf.clear();
                }
                outChannel.force(true);
            }finally {
                outChannel.close();
                inChannel.close();
                fos.close();
                fis.close();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("base复制毫秒数：" + (endTime - startTime) );
        }catch (IOException e){
            e.printStackTrace();
        }



    }

    public void read(){
        try{
            String fileName = "";
            RandomAccessFile rmFile = new RandomAccessFile(fileName, "rw");
            FileChannel channel = rmFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(20);
            int length = -1;
            while ((length = channel.read(buf)) != -1){

            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }


}
