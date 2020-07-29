package com.xphwv.netty.nio;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by xupan on 2020/5/26
 */
@Slf4j
public class NioEchoServer {
    final static int SELECT_TIME_OUT = 1000;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(SELECT_TIME_OUT) == 0) {
                log.info("wait .....");
            }
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector,SelectionKey.OP_READ);
                    log.info("connecting .....");
                } else if (key.isReadable()) {
                    log.info("reading .....");
                    SocketChannel clientChannel= (SocketChannel) key.channel();
                    ByteBuffer byteBuffer= (ByteBuffer) key.attachment();

                } else if (key.isValid() || key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    buffer.flip();


                    clientChannel.write(buffer);

                    if (!buffer.hasRemaining()) {
                        clientChannel.register(selector, SelectionKey.OP_READ);
                    }
                    log.info("writing .....");
                }
            }
        }
    }
}
