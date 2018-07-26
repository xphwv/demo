package com.xphwv.netty.enums;
enum Planets implements Named {
	Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune;
	// name() is implemented automagically.
	public int order() {
		return ordinal() + 1;
	}
}
interface Named {
	public String name();

	public int order();
}
