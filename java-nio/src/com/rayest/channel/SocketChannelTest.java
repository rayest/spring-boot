package com.rayest.channel;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import static org.junit.Assert.*;

public class SocketChannelTest {

    @Test
    public void test_open() throws IOException {
        SocketChannel channel = SocketChannel.open();
        assertTrue(channel.isOpen());
    }

    @Test
    public void test_close() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.close();
        assertFalse(channel.isOpen());
    }

    @Test
    public void test_non_blocking() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        assertFalse(socketChannel.isBlocking());
    }
}
