package com.rayest.channel;

import com.rayest.Note;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileChannelTest {
    private FileChannel accessFileChannel;
    private FileChannel fileInputStreamChannel;
    private FileChannel fileOutputStreamChannel;
    private RandomAccessFile randomAccessFile;

    @Before
    public void setUp() throws Exception {
        String pathname1 = "file/random-access-file";
        randomAccessFile = new RandomAccessFile(pathname1, "r");
        accessFileChannel = randomAccessFile.getChannel();

        String pathname2 = "file/file-input-stream-file";
        FileInputStream fileInputStream = new FileInputStream(pathname2);
        fileInputStreamChannel = fileInputStream.getChannel();

        String pathname3 = "file/file-input-stream-file";
        FileOutputStream fileOutputStream = new FileOutputStream(pathname3);
        fileOutputStreamChannel = fileOutputStream.getChannel();
    }

    @After
    public void tearDown() throws Exception {
        accessFileChannel.close();
        fileInputStreamChannel.close();
        fileOutputStreamChannel.close();
    }

    @Test
    public void test_open_by_random_access_file() {
        assertTrue(accessFileChannel.isOpen());
    }

    @Test
    public void test_open_by_file_input_stream() {
        assertTrue(fileInputStreamChannel.isOpen());
    }

    @Test
    @Note("Reads a sequence of bytes from this channel into the given buffer.")
    public void test_read_by_random_access_file() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        accessFileChannel.read(byteBuffer);

        assertEquals(10, byteBuffer.position());
    }

    @Test
    @Note("Sets the file-pointer offset")
    public void test_seek() throws IOException {
        assertEquals(0, accessFileChannel.position());

        randomAccessFile.seek(100);
        assertEquals(100, accessFileChannel.position());

        accessFileChannel.position(200);
        assertEquals(200, accessFileChannel.position());
    }

    @Test
    public void test_file_lock() throws IOException {
        FileLock fileLock = fileOutputStreamChannel.lock();
        assertFalse("默认是独占锁", fileLock.isShared());
        assertTrue("锁是有效的", fileLock.isValid());
        fileLock.release();
    }

    @Test
    public void test_file_channel_map() throws IOException {
        MappedByteBuffer mappedByteBuffer = fileInputStreamChannel.map(FileChannel.MapMode.READ_ONLY, 0, 10);
        assertEquals(0, mappedByteBuffer.position());
    }
}
