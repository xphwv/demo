package com.xphwv.google.guice.h1;

import java.util.concurrent.TimeUnit;

import com.xphwv.google.guice.scope.ThreadServiceScope;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import com.xphwv.google.guice.an.One;
import com.xphwv.google.guice.an.Two;

public class HelloTest {
	/**
	 * 自动寻找
	 */
	@Test
	public void testAuto() {
		Injector injector = Guice.createInjector();
		IHello hello = injector.getInstance(HelloImpl.class);
		hello.say();
	}

	@Test
	public void testInjector() {
		Injector injector = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(IHello.class).to(HelloImpl.class);
			}
		});
		IHello hello = injector.getInstance(IHello.class);
		hello.say();
	}

	/**
	 * 提供了一个方式（Provider<T>）<br/>
	 * 允许自己提供构造对象的方式
	 */
	@Test
	public void testProvider() {
		Injector injector = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(IHello.class).toProvider(new Provider<IHello>() {
					@Override
					public IHello get() {
						return new HelloImpl();
					}
				});
			}
		});
		IHello hello = injector.getInstance(IHello.class);
		hello.say();
	}

	@SuppressWarnings("static-access")
	@Test
	public void testPolymorphism() {
		HelloService service = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(IHello.class).annotatedWith(Names.named("one")).to(HelloImpl.class);
				binder.bind(IHello.class).annotatedWith(Names.named("two")).to(HelloAgainImpl.class);
				binder.requestStaticInjection(HelloService.class);
			}
		}).getInstance(HelloService.class);

		service.getHello1().say();
		service.getHello2().say();
	}

	@Test
	public void testPolymorphismAn() {
		HelloAnService service = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(IHello.class).annotatedWith(One.class).to(HelloImpl.class);
				binder.bind(IHello.class).annotatedWith(Two.class).to(HelloAgainImpl.class);
			}
		}).getInstance(HelloAnService.class);

		service.getHello1().say();
		service.getHello2().say();
	}

	@Test
	public void testThreadScope() throws Exception {
		final Injector injector = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(IHello.class).to(HelloImpl.class).in(new ThreadServiceScope());
			}
		});
		for (int i = 1; i < 4; i++) {
			new Thread("Thread-" + i) {
				public void run() {
					for (int m = 1; m < 4; m++) {
						IHello hello = injector.getInstance(IHello.class);
						System.out.println(this.getName() + "-" + m + "-" + hello.hashCode());
					}
					try {
						TimeUnit.SECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

}
