package com.xphwv.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket s;

	public ServerThread(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		System.out.println("running..");
		BufferedReader in = null;
		BufferedWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			String inBody = null;
			inBody = in.readLine();
			String id = String.valueOf((this.getId()));
			System.out.println(inBody + ",t:" + id);
			out.write(id);
			if (inBody.equals("end")) {
				out.write("close");
				close(in, out);
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				close(in, out);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void close(BufferedReader in, BufferedWriter out) throws IOException {
		out.close();
		in.close();
		this.s.close();
	}

}