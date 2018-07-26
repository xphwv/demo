package com.xphwv.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.xphwv.io.ServerThread;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2015年10月28日 下午3:34:44
 * @version 1.0
 */
public class Server {
	public static void main(String[] args) throws IOException {
		int port = 8080;
		ServerSocket ss = new ServerSocket(port);
		Socket s = null;
		while (true) {
			s = ss.accept();
			new ServerThread(s).start();
		}
	}
}

