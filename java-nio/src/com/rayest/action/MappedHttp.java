package com.rayest.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedHttp {
    public static final String OUTPUT_FILE = "file/mapped-http.out";
    public static final String INPUT_FILE = "file/mapped-http.in";

    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream(INPUT_FILE);
            FileChannel fc = fileInputStream.getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

            FileOutputStream pos = new FileOutputStream(OUTPUT_FILE);
            FileChannel channel = pos.getChannel();

            while (channel.write(byteBuffer) > 0) {
            }
            channel.close();
            System.out.println("output written to " + OUTPUT_FILE);
        } catch (IOException e) {
            System.out.println("出错了");
        }
    }
}
