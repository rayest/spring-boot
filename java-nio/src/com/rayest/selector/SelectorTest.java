package com.rayest.selector;

import com.rayest.Note;
import org.junit.Test;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SelectorTest {

    @Test
    @Note("通过静态工厂方法打开一个选择器")
    public void test_open() throws IOException {
        Selector selector = Selector.open();
        assertTrue(selector.isOpen());
    }

    @Test
    public void test_register() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        assertTrue("通道已注册到选择器上", serverSocketChannel.isRegistered());
    }

    @Test
    public void test_valid_ops_of_socket_channel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        assertEquals(SelectionKey.OP_READ | SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE, socketChannel.validOps());
    }

    @Test
    public void test_valid_ops_of_server_socket_channel() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        assertEquals(SelectionKey.OP_ACCEPT, serverSocketChannel.validOps());
    }

}
