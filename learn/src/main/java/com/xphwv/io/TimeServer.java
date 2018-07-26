package com.xphwv.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServer {

	public static void main(String[] args) throws IOException {
		int port = 8080;
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			Socket socket = null;
			TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50, 10000);// 创建IO任务线程池
			while (true) {
				socket = server.accept();
				singleExecutor.execute(new ServerThread(socket));
			}
		} finally {
			if (server != null) {
				server.close();
				server = null;
			}
		}
	}
}

class TimeServerHandlerExecutePool {
	private ExecutorService executor;

	public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
		executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS
				, new ArrayBlockingQueue(queueSize));
	}

	public void execute(java.lang.Runnable task) {
		executor.execute(task);
	}
}
