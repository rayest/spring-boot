package com.rayest.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileTest {
    public static void main(String[] args) {
//        readNIO();
        writeNIO();
    }

    private static void writeNIO() {
        String pathname = "write-from-out";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(pathname));
            FileChannel channel = fileOutputStream.getChannel();
            int length;
            ByteBuffer byteBuffer = Charset.forName("utf8").encode("123456789");
            while ((length = channel.write(byteBuffer)) != 0){
                System.out.println("写入的长度:" + length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readNIO() {
        try {
            String pathname = "read-from-here";
            FileInputStream fileInputStream = new FileInputStream(new File(pathname));
            FileChannel channel = fileInputStream.getChannel();
            int capacity = 8;
            ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);

            System.out.println("初始化值 - limit:" + byteBuffer.limit() + " capacity:" + byteBuffer.capacity() + " position:" + byteBuffer.position());

            int length;
            while ((length = channel.read(byteBuffer)) != -1) {

                System.out.println("clear 前 - limit:" + byteBuffer.limit() + " capacity:" + byteBuffer.capacity() + " position:" + byteBuffer.position());

                byteBuffer.clear();
                byte[] bytes = byteBuffer.array();
                String str = new String(bytes, 0, length);

                System.out.println(str);
                System.out.println("clear 后 - limit:" + byteBuffer.limit() + " capacity:" + byteBuffer.capacity() + " position:" + byteBuffer.position());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
