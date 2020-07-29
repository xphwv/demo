package com.xphwv.io.nio.intBuffer;

import java.nio.IntBuffer;

/**
 * Created by xupan on 2020/5/20
 */
public class IntBufferTest {
    public static void main(String[] args) {
        IntBuffer intBuffer=IntBuffer.allocate(10);
        intBuffer.put(123123).put(232323);
        System.out.println(intBuffer.position());
        System.out.println(intBuffer.limit());
        intBuffer.flip();
        System.out.println(intBuffer.position());
        System.out.println(intBuffer.limit());
        System.out.println(intBuffer.get());
        System.out.println(intBuffer.get());
        System.out.println(intBuffer.position());
        System.out.println(intBuffer.limit());
        intBuffer.compact();
        System.out.println(intBuffer.position());
        System.out.println(intBuffer.limit());
    }
}
