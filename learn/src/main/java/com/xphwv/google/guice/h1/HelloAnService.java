package com.xphwv.google.guice.h1;

import com.google.inject.Inject;
import com.xphwv.google.guice.an.One;
import com.xphwv.google.guice.an.Two;

public class HelloAnService {
	@Inject
	@One
	private  IHello hello1;
	@Inject
	@Two
	private  IHello hello2;

	public  IHello getHello1() {
		return hello1;
	}

	public  IHello getHello2() {
		return hello2;
	}

}
