package com.rayest.buffer;

import com.rayest.Note;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BufferTest {

    @Test
    public void test_buffer_before_put() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        assertEquals(8, byteBuffer.limit());
        assertEquals(8, byteBuffer.capacity());
        assertEquals(0, byteBuffer.position());
    }

    @Test
    public void test_buffer_after_put() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put((byte) 'H').put((byte) 'i');
        assertEquals(8, byteBuffer.limit());
        assertEquals(8, byteBuffer.capacity());
        assertEquals(2, byteBuffer.position());
    }

    @Test
    public void test_buffer_get() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put((byte) 'H').put((byte) 'i');
        assertEquals('H', byteBuffer.get(0));
        assertEquals('i', byteBuffer.get(1));
    }

    @Test
    @Note("flip 翻转操作。limit = position；position = 0")
    public void test_buffer_flip() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put((byte) 'H').put((byte) 'i');
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
        byteBuffer.put((byte) 'H').put((byte) 'i');
        assertEquals(2, byteBuffer.position());
        assertEquals(8, byteBuffer.limit());

        byteBuffer.rewind();

        assertEquals(0, byteBuffer.position());
        assertEquals(8, byteBuffer.limit());
    }

    @Test
    public void test_remaining() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put((byte) 'H').put((byte) 'i');
        assertEquals(6, byteBuffer.remaining());

        byteBuffer.flip();
        assertEquals(2, byteBuffer.remaining());
    }

    @Test
    @Note("clear 方法释放缓冲区，但并不改变任何元素")
    public void test_clear() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put((byte) 'H').put((byte) 'i');
        assertEquals(6, byteBuffer.remaining());

        byteBuffer.clear();

        assertEquals(8, byteBuffer.remaining());
    }

    @Test
    @Note("compact 未读元素下移覆盖已读数据的空间. position等量偏移。limit = capacity")
    public void test_compact() {
        int capacity = 8;
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        byteBuffer.put((byte) 'H').put((byte) 'e').put((byte) 'l').put((byte) 'l').put((byte) 'o').put((byte) 'w');

        byteBuffer.flip();
        byteBuffer.get();
        byteBuffer.get();
        assertEquals(2, byteBuffer.position());
        assertEquals(6, byteBuffer.limit());

        byteBuffer.compact();
        assertEquals(2 + 2, byteBuffer.position());
        assertEquals(capacity, byteBuffer.limit());
    }

    @Test
    @Note("mark(): mark = position; reset(): position = mark。似是一对反操作")
    public void test_mark_reset() {
        int capacity = 8;
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        byteBuffer.put((byte) 'H').put((byte) 'i');

        assertEquals(2, byteBuffer.position());
        byteBuffer.mark(); // set mark = position = 2

        byteBuffer.put((byte) 'l');
        assertEquals(3, byteBuffer.position());

        byteBuffer.reset(); // set position = mark = 2。
        assertEquals(2, byteBuffer.position());
    }

    @Test
    @Note("比较缓冲区是否相等: 1 元素类型相同；2可操作元素个数相同；3 get方法返回数据序列一致")
    public void test_compare() {
        int capacity = 8;
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(capacity);
        byteBuffer1.put((byte) 'H').put((byte) 'i');
        byteBuffer1.flip();

        ByteBuffer byteBuffer2 = ByteBuffer.allocate(capacity);
        byteBuffer2.put((byte) 'i').put((byte) 'H').put((byte) 'i');
        byteBuffer2.flip();
        byteBuffer2.get();

        assertEquals(byteBuffer1.remaining(), byteBuffer2.remaining());
        assertEquals(byteBuffer1.get(), byteBuffer2.get());
        assertEquals(byteBuffer1, byteBuffer2);
    }

    @Test
    @Note("小型缓冲区向大数组填数据时，需要指定填充的 offset 和 length")
    public void test_batch_get_from_buffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put((byte) 'H').put((byte) 'i');
        byteBuffer.flip(); // 确保数据是可读取的

        byte[] byteArray = new byte[8];
        byteBuffer.get(byteArray, 0, 2);

        assertEquals('H', byteArray[0]);
        assertEquals('i', byteArray[1]);
    }

    @Test
    public void test_batch_put_to_buffer() {
        byte[] byteArray = new byte[]{'H', 'i'};
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put(byteArray, 0, 2);

        assertEquals(2, byteBuffer.position());

        byteBuffer.flip();
        assertEquals('H', byteBuffer.get(0));
        assertEquals('i', byteBuffer.get(1));
    }

    @Test
    @Note("创建缓冲区对象，并且在堆上分配私有空间以存储元素")
    public void test_allocate() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        assertEquals(0, byteBuffer.position());
        assertEquals(8, byteBuffer.limit());
        assertEquals(8, byteBuffer.capacity());
    }

    @Test
    @Note("创建缓冲区对象，但不分配空间存储元素，通过数组实现数据存储")
    public void test_wrap_of_common() {
        byte[] byteArray = new byte[2];
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);

        byteBuffer.put((byte) 'H').put((byte) 'i');

        assertEquals('H', byteArray[0]);
        assertEquals('i', byteArray[1]);
    }

    @Test
    @Note("CharBuffer 独有的包装操作")
    public void test_wrap_from_char_buffer() {
        CharBuffer charBuffer = CharBuffer.wrap("Hello world");
        assertEquals(11, charBuffer.limit());
        assertEquals(11, charBuffer.capacity());
        assertEquals('H', charBuffer.get(0));
        assertEquals('d', charBuffer.get(charBuffer.length() - 1));
    }

    @Test
    public void test_duplicate() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.position(3).limit(6).mark().position(5);

        assertEquals(5, byteBuffer.position());
        assertEquals(6, byteBuffer.limit());

        ByteBuffer duplicate = byteBuffer.duplicate();

        assertEquals(5, duplicate.position());
        assertEquals(6, duplicate.limit());
    }

    @Test
    public void test_slice() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.position(3).limit(5);

        ByteBuffer slice = byteBuffer.slice();

        assertEquals(0, slice.position());
        assertEquals(2, slice.limit());
        assertEquals(2, slice.capacity());
    }

    @Test
    public void test_allocate_direct() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8);
        assertTrue(byteBuffer.isDirect());
    }
}
