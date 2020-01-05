package com.rayest.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerSocketChannelTest {

    @Test
    public void test_open() throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        assertTrue(channel.isOpen());
    }

    @Test
    public void test_close() throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.close();
        assertFalse(channel.isOpen());
    }
}
