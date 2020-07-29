package com.xphwv.netty.nio._2_3_2;

import java.nio.channels.SelectionKey;

public interface IOHandler {

	public void process(SelectionKey selectionKey);
}
