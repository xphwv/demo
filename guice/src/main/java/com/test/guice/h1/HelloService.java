package com.test.guice.h1;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class HelloService {
	@Inject
	@Named("one")
	private static IHello hello1;
	@Inject
	@Named("two")
	private static IHello hello2;

	public static IHello getHello1() {
		return hello1;
	}

	public static IHello getHello2() {
		return hello2;
	}

}
