package com.xphwv.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2015年11月26日 下午2:41:43
 * @version 1.0
 */
public class Server {
	private static final Logger loger=Logger.getLogger(Server.class);
	private static Selector selector = null;
	private static ByteBuffer readBuffer = null;
	private final static InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8080);

	public static void main(String[] args) throws Exception {
		loger.debug("服务器启动");
		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.configureBlocking(false);channel.socket().bind(address);
		selector = Selector.open();	channel.register(selector, SelectionKey.OP_ACCEPT);
		readBuffer = ByteBuffer.allocate(1024);	loger.debug("参数初始化完成");
		while (true) {
			loger.debug("进入监控");selector.select();
			Iterator<SelectionKey> ites = selector.keys().iterator();
			while (ites.hasNext()) {
				SelectionKey key = ites.next();
				handelKey(key);
			}
		}
	}

	private static void handelKey(SelectionKey key) throws Exception {
		SocketChannel channel = null;
		if (key.isAcceptable()) {loger.debug("有客户端连接");
			channel = ((ServerSocketChannel) key.channel()).accept();
			channel.configureBlocking(false);
			channel.register(selector, SelectionKey.OP_READ);
		} else if (key.isReadable()) {
			channel = (SocketChannel) key.channel();readBuffer.clear();
			CharBuffer charBuffer = CharsetHelper.decode(readBuffer);
			String msg = charBuffer.toString();
			loger.info("服务器接收到消息：" + msg);
			String answer = "我是服务端响应！";
			channel.write(CharsetHelper.encode(CharBuffer.wrap(answer)));
		}
	}
}
