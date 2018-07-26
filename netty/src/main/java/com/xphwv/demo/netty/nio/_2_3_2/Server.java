package com.xphwv.demo.netty.nio._2_3_2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {
	final static int PORT = 9000;

	public static void main(String[] args) throws IOException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"), PORT));
		ssc.configureBlocking(false);
		Selector selector = Selector.open();
		new Thread(new ReactorTask()).start();

		ssc.register(selector, SelectionKey.OP_ACCEPT, new AccepterHandler());
		ssc.register(selector, SelectionKey.OP_READ, new InputHandler());

		while (selector.select() > 0) {
			Iterator<SelectionKey> keysIterator = selector.selectedKeys().iterator();
			while (keysIterator.hasNext()) {
				SelectionKey key = keysIterator.next();
				execute((ServerSocketChannel) key.channel());
				((IOHandler) key.attachment()).process(key);
			}
		}
	}

	private static void execute(ServerSocketChannel channel) {

	}
}

class ReactorTask implements Runnable {

	@Override
	public void run() {
		System.out.println("init task");
	}

}

class AccepterHandler implements IOHandler {
	public void process(SelectionKey selectionKey) {
		ServerSocketChannel ser = (ServerSocketChannel) selectionKey.channel();
		if (selectionKey.isValid()) {
			try {
				SocketChannel socketChannel = ser.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class InputHandler implements IOHandler {
	public void process(SelectionKey selectionkey) {
		if (selectionkey.isValid()) {
			SocketChannel socketChannel = (SocketChannel) selectionkey.channel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			int lent = 0;
			try {
				lent = socketChannel.read(byteBuffer);

				while (lent > 0) {
					lent = socketChannel.read(byteBuffer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (lent == 0) {

			}
			if (lent == -1) {

			}
		}
	}
}