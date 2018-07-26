package com.xphwv.io.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.log4j.Logger;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2015年11月13日 下午4:37:32
 * @version 1.0
 */
public class FileOperate {
	private static final Logger loger=Logger.getLogger(FileOperate.class);
	public static void main(String[] args) throws Exception {
		String dir=System.getProperty("user.dir");
		loger.debug("dir:"+dir);
		RandomAccessFile access= new RandomAccessFile(dir+"/src/main/resources/files/data.txt", "rw");
		FileChannel channel=access.getChannel();
		ByteBuffer buffer=ByteBuffer.allocate(512);
		int byteRead=channel.read(buffer);
		while(byteRead!=-1){
			buffer.flip();
			while(buffer.hasRemaining()){
				loger.info(buffer.get());
			}
		}
		buffer.clear();
//		buffer.put(byteRead)
		
		
	}
}
