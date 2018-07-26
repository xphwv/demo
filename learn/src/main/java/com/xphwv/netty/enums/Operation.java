package com.xphwv.netty.enums;
public enum Operation {
  PLUS   { double eval(double x, double y) { return x + y; } },
  MINUS  { double eval(double x, double y) { return x - y; } },
  TIMES  { double eval(double x, double y) { return x * y; } },
  DIVIDE { double eval(double x, double y) { return x / y; } };

	abstract double eval(double x, double y);

	public static void main(String[] args) {
		System.out.println(Operation.PLUS.eval(1, 2));
	}
}