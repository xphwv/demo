package com.xphwv.io.nio.file;

import lombok.extern.slf4j.Slf4j;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * TODO
 *
 * @author XuPan
 * @version 1.0
 * @date 2015年11月13日 下午4:37:32
 */
@Slf4j
public class FileOperate {
    public static void main(String[] args) throws Exception {
        String dir = System.getProperty("user.dir");
        log.debug("dir:" + dir);
        RandomAccessFile access = new RandomAccessFile(dir + "/learn/data/data.txt", "rw");

        FileChannel channel = access.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(128);
        System.out.println("init 限制是：" + buffer.limit() + ",容量是：" + buffer.capacity() + " ,位置是：" + buffer.position());
        System.out.println("--------------------------");
        int byteRead;
        StringBuilder builder = new StringBuilder();
        while ((byteRead = channel.read(buffer)) != -1) {
            System.out.println("before,限制是：" + buffer.limit() + ",容量是：" + buffer.capacity() + " ,位置是：" + buffer.position());
            buffer.rewind();
            System.out.println("after,限制是：" + buffer.limit() + ",容量是：" + buffer.capacity() + " ,位置是：" + buffer.position());

            builder.append(new String(buffer.array()));
        }
        buffer.clear();
        System.out.println(builder.toString());
//		buffer.put(byteRead)


    }
}
