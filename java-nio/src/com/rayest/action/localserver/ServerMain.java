package com.rayest.action.localserver;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerMain {
    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(1234));
            serverSocketChannel.configureBlocking(false);
            while (true) {
                System.out.println("等待连接...");
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel == null) {
                    Thread.sleep(2000);
                } else {
                    System.out.println("连接 from: " + socketChannel.getRemoteAddress());
                    socketChannel.close();
                }

            }
        } catch (Exception e) {
            System.out.println("出错了");
        }
    }
}
