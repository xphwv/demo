package com.test.guice.eventbus;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MsgCenter1 {
	public static void main(String[] args) {
		Subject subject=new Subject();
		subject.put(new Ob1());
		subject.put(new Ob2());
		subject.put(new Ob2());
		subject.update();
	}
}

class Subject {
	BlockingQueue<Observer> queue = new ArrayBlockingQueue<Observer>(100, false);

	public boolean put(Observer o) {
		try {
			queue.put(o);
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}

	public Observer poll() {
		return queue.poll();
	}

	public void update() {
		for (Observer observer : queue) {
			observer.update();
		}
	}
}

abstract class Observer {
	public void dosomething() {
	}

	public abstract void update();
}

class Ob1 extends Observer {

	@Override
	public void update() {
		System.out.println("222");

	}

}

class Ob2 extends Observer {

	@Override
	public void update() {
		System.out.println("111");

	}

}
