package com.xphwv.netty.nio._2_3_3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

class MultiplexerTimeServer implements Runnable {
	static Logger logger = Logger.getLogger(MultiplexerTimeServer.class);
	private volatile boolean stop;
	private Selector selector;
	private ServerSocketChannel servChannel;

	public MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();
			servChannel = ServerSocketChannel.open();
			servChannel.configureBlocking(false);
			servChannel.socket().bind(new InetSocketAddress("127.0.0.1", port), 1024);
			servChannel.register(selector, SelectionKey.OP_ACCEPT);
			logger.info("服务器启动成功：port=" + port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void stop() {
		this.stop = true;
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				SelectionKey key = null;
				while (iterator.hasNext()) {
					key = iterator.next();
					iterator.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						logger.error("业务处理异常,", e);
						key.cancel();
						if (key.channel() != null) {
							key.channel().close();
						}
					}
				}

			} catch (IOException e) {
				logger.error("服务执行异常,", e);
			}
		}
		if (selector != null) {
			try {
				selector.close();
			} catch (IOException e) {
				logger.error("服务关闭异常,", e);
			}
		}
	}

	public void handleInput(SelectionKey key) throws IOException {
		if (key.isValid()) {
			if (key.isAcceptable()) {
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);
			} else if (key.isReadable()) {
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer readBuf = ByteBuffer.allocate(1024);
				int count = sc.read(readBuf);
				if (count > 0) {
					readBuf.flip();
					byte[] bytes = new byte[readBuf.remaining()];
					readBuf.get(bytes);
					String body = new String(bytes, "utf-8");
					logger.info("接受到信息：" + body);
					String currentTime = new Date(System.currentTimeMillis()).toString() + "\r\n";
					doWrite(sc, currentTime);
				} else if (count < 0) {
					key.cancel();
					sc.close();
				}
			}
		}
	}

	public void doWrite(SocketChannel sc, String response) throws IOException {
		if (response != null && response.trim().length() > 0) {
			byte[] bytes = response.getBytes();
			ByteBuffer writeBuf = ByteBuffer.allocate(bytes.length);
			writeBuf.put(bytes);
			writeBuf.flip();
			sc.write(writeBuf);
		}
	}
}