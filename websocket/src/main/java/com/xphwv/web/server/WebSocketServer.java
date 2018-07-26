package com.xphwv.web.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2014年10月24日 上午11:16:57
 * @version 1.0
 */
@ServerEndpoint(value = "/websocket", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class WebSocketServer {
	private static Map<String, Session> map=new ConcurrentHashMap<String,Session>();
	@OnMessage
	public void onMessage(Message message, Session session) throws IOException, EncodeException {
		System.out.println("客户端请求："+message.getSubject()+":"+message.getContent());
		System.out.println("请输入你的主体：");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String content=br.readLine();	
		System.out.println(content);
		Message response = new Message();
		response.setSubject("Response to " + message.getSubject());
		response.setContent("echo " + content);
		System.out.println(session.getId());
		session.getBasicRemote().sendObject(response);
	}

	@OnOpen
	public void onOpen(Session session) {
		map.put(session.getId(),session);
		System.out.println("Client connected");
	}

	@OnClose
	public void onClose(Session session) {
		map.remove(session.getId());
		System.out.println("Connection closed");
	}
}
