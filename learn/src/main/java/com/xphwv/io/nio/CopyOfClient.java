package com.xphwv.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class CopyOfClient implements Runnable {
	private static final Logger loger = Logger.getLogger(Server.class);
	private final static InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8080);

	public static void main(String[] args) {
		// 种多个线程发起Socket客户端连接请求
		for (int i = 0; i < 1; i++) {
			CopyOfClient c = new CopyOfClient();
			new Thread(c).start();
		}
	}

	@Override
	public void run() {
		SocketChannel channel = null;
		Selector selector = null;
		try {
			channel = SocketChannel.open();
			channel.configureBlocking(false);
			// 请求连接
			channel.connect(address);
			selector = Selector.open();
			channel.register(selector, SelectionKey.OP_CONNECT);

			selector.select();
			Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();
				ite.remove();
				if (key.isConnectable()) {
					if (channel.isConnectionPending()) {
						if (channel.finishConnect()) {
							// 只有当连接成功后才能注册OP_READ事件
							key.interestOps(SelectionKey.OP_READ);
							String msg = "客户端：" + Thread.currentThread().getId() + "连接成功了";
							channel.write(CharsetHelper.encode(CharBuffer.wrap(msg)));
						} else {
							key.cancel();
						}
					}
				} else if (key.isReadable()) {
					ByteBuffer byteBuffer = ByteBuffer.allocate(128);
					channel.read(byteBuffer);
					byteBuffer.flip();
					CharBuffer charBuffer = CharsetHelper.decode(byteBuffer);
					String clickMs = "客户端：" + Thread.currentThread().getId();
					String answer = charBuffer.toString();
					loger.info(clickMs + "接收到消息：" + answer);
					channel.write(CharsetHelper.encode(CharBuffer.wrap(clickMs + "响应了")));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (selector != null) {
				try {
					selector.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}