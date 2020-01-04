package com.rayest.buffer;

import com.rayest.Note;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class BufferTest {

    @Test
    public void test_buffer_before_put(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        assertEquals(8, byteBuffer.limit());
        assertEquals(8, byteBuffer.capacity());
        assertEquals(0, byteBuffer.position());
    }

    @Test
    public void test_buffer_after_put() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put((byte)'H').put((byte) 'i');
        assertEquals(8, byteBuffer.limit());
        assertEquals(8, byteBuffer.capacity());
        assertEquals(2, byteBuffer.position());
    }

    @Test
    public void test_buffer_get() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put((byte)'H').put((byte) 'i');
        assertEquals('H', byteBuffer.get(0));
        assertEquals('i', byteBuffer.get(1));
    }

    @Test
    @Note("flip 翻转操作。limit = position；position = 0")
    public void test_buffer_flip() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put((byte)'H').put((byte) 'i');
        assertEquals(2, byteBuffer.position());
        assertEquals(8, byteBuffer.limit());

        byteBuffer.flip();

        assertEquals(0, byteBuffer.position());
        assertEquals(2, byteBuffer.limit());
    }


    @Test
    @Note("rewind方法。仅将 position = 0，之后的数据会在下次操作时被覆盖")
    public void test_buffer_rewind() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put((byte)'H').put((byte) 'i');
        assertEquals(2, byteBuffer.position());
        assertEquals(8, byteBuffer.limit());

        byteBuffer.rewind();

        assertEquals(0, byteBuffer.position());
        assertEquals(8, byteBuffer.limit());

    }
}
