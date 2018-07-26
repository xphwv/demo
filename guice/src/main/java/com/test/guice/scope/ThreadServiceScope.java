package com.test.guice.scope;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

public class ThreadServiceScope implements Scope {
	static ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();

	@Override
	public <T> Provider<T> scope(Key<T> key, final Provider<T> unscoped) {
		return new Provider<T>() {
			@Override
			public T get() {
				T instance = (T) threadLocal.get();
				if (instance == null) {
					instance = unscoped.get();
					threadLocal.set(instance);
				}
				return instance;
			}
		};
	}
}
