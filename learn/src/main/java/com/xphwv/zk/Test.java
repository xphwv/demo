package com.xphwv.zk;

import java.io.IOException;
import java.util.Map;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2016年8月16日 下午4:05:09
 * @version 1.0
 */

public class Test {
	private final static String ZK_URL = "127.0.0.1:2181";
	private final static int ZK_TIMEOUT = 2000;
	public static void main(String[] args) throws Exception {
		// 创建一个与服务器的连接
		ZooKeeper zk = new ZooKeeper(ZK_URL, ZK_TIMEOUT, new Watcher() {
			// 监控所有被触发的事件
			public void process(WatchedEvent event) {
				System.out.println("已经触发了" + event.getType() + "事件！");
			}
		});
		// 创建一个目录节点
		zk.create("/zookeeper/xphwv", "0".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		// 创建一个子目录节点
		zk.create("/zookeeper/xphwv/testChildPathOne", "testChildDataOne".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(new String(zk.getData("/zookeeper/xphwv", false, null)));
		// 取出子目录节点列表
		System.out.println(zk.getChildren("/zookeeper/xphwv", true));
		// 修改子目录节点数据
		zk.setData("/zookeeper/xphwv/testChildPathOne", "modifyChildDataOne".getBytes(), -1);
		System.out.println("目录节点状态：[" + zk.exists("/zookeeper/xphwv", true) + "]");
		// 创建另外一个子目录节点
		zk.create("/zookeeper/xphwv/testChildPathTwo", "testChildDataTwo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(new String(zk.getData("/zookeeper/xphwv/testChildPathTwo", true, null)));
		// 删除子目录节点
		zk.delete("/zookeeper/xphwv/testChildPathTwo", -1);
		zk.delete("/zookeeper/xphwv/testChildPathOne", -1);
		// 删除父目录节点
		zk.delete("/zookeeper/xphwv", -1);
		// 关闭连接
		zk.close();
	}
}
