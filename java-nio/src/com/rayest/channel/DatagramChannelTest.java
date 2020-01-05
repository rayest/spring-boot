package com.rayest.channel;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DatagramChannelTest {

    @Test
    public void test_open() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        assertTrue(channel.isOpen());
    }

    @Test
    public void test_close() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.close();
        assertFalse(channel.isOpen());
    }
}
