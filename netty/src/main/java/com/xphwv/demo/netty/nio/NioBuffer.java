package com.xphwv.demo.netty.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioBuffer {
	private static RandomAccessFile raf;

	public static void main(String[] args) {
		FileChannel channel = null;
		try {
			raf = new RandomAccessFile(Constants.PATH + "/data/d1.txt", "rw");
			channel = raf.getChannel();
			int size = (int) channel.size();
			ByteBuffer buffer = ByteBuffer.allocate(100);
			int read = channel.read(buffer);
			StringBuffer sb = new StringBuffer(size);
			while (read != -1) {
				buffer.flip();

				while (buffer.hasRemaining()) {
					sb.append((char) buffer.get());
				}
				buffer.clear();
				read = channel.read(buffer);
			}
			System.out.println(sb.toString());
			buffer = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != channel)
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}
}
