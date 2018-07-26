package com.xphwv.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2015年10月28日 下午4:42:07
 * @version 1.0
 */
public class Click {
	public static void main(String[] args) throws IOException {
		int port=8080;
		Socket s=new Socket("127.0.0.1", port);
		PrintWriter out=new PrintWriter(s.getOutputStream(),true);
		out.print("aaa");
		out.close();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		System.out.println(in.readLine());
		in.close();	
		s.close();
		
		out=new PrintWriter(s.getOutputStream(),true);
		out.print("end");
		out.close();
	}
}
