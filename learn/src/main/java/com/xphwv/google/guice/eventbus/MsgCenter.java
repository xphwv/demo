package com.xphwv.google.guice.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class MsgCenter {
	public static EventBus eventBus = new EventBus();

	public static void main(String[] args) {
		Observer1Bus o1 = new Observer1Bus();
		Observer2Bus o2 = new Observer2Bus();
		eventBus.register(o1);
		eventBus.register(o2);

		// ֻ��ע��Ĳ�������ΪString�ķ����ᱻ����
		eventBus.post("post string method");
		eventBus.post(1);
		eventBus.post(1L);
	}
}

class Observer1Bus {
	@Subscribe
	public void obMethod1(String msg) {
		System.out.println("obmethod1��" + msg);
	}

	@Subscribe
	public void obMethod2(Integer msg) {
		System.out.println("obmethod2��" + msg);
	}
}

class Observer2Bus {
	@Subscribe
	public void obMethod1(int msg) {
		System.out.println("obmethod1��" + msg);
	}

	@Subscribe
	public void obMethod2(Long msg) {
		System.out.println("obmethod2��" + msg);
	}
}
