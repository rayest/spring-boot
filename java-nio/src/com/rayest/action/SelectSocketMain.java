package com.rayest.action;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectSocketMain {
    public static int port = 1234;

    public static void main(String[] args) {
        new SelectSocketMain().go();
    }

    private void go() {
        System.out.println("port: " + port);
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", port));
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                int n = selector.select();
                if (n == 0) {
                    continue;
                }

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel channel = server.accept();
                        registerChannel(selector, channel, SelectionKey.OP_READ);
                        sayHello(channel);
                    }
                    if (selectionKey.isReadable()) {
                        readDataFromSocket(selectionKey);
                    }
                    iterator.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    private void readDataFromSocket(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        int count;
        buffer.clear();
        while ((count = socketChannel.read(buffer)) > 0){
            buffer.flip();
            while (buffer.hasRemaining()){
                socketChannel.write(buffer);
            }
            buffer.clear();
        }
        if (count < 0){
            socketChannel.close();;
        }
    }


    private void sayHello(SocketChannel channel) throws IOException {
        buffer.clear();
        buffer.put("Hi there".getBytes());
        buffer.flip();
        channel.write(buffer);
    }

    private void registerChannel(Selector selector, SocketChannel channel, int ops) throws IOException {
        if (null == channel) {
            return;
        }
        channel.configureBlocking(false);
        channel.register(selector, ops);
    }
}
