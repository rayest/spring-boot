package com.rayest.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileTest {
    public static void main(String[] args) {
        readNIO();
    }

    private static void readNIO() {
        try {
            String pathname = "test";
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
