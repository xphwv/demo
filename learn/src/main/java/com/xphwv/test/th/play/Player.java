/**  
 * @Copyright 五八信息技术有限公司 
 */
package com.xphwv.test.th.play;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2014-5-9 下午4:56:13
 * @version 1.0
 */
public class Player extends Thread {
	private int playerId;

	public Player(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public void run() {
		boolean success = false;
		while (!success) {
			int value = Attempt.guess(Judge.MAX_INT);
			success = Judge.judge(value);
			System.out.println(String.format("Plyaer %s Attempts %s and %s",
					playerId, value, success ? " Success" : "Failed"));
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Attempt.review(String
				.format("[IFNO] Plyaer %s Completed by ", playerId));
	}
}
