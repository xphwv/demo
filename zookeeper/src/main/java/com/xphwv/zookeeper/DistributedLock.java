package com.xphwv.zookeeper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class DistributedLock implements Watcher {

	private static Logger logger = Logger.getLogger(DistributedLock.class);
	private CountDownLatch conncedSemaphore = new CountDownLatch(1);
	private ZooKeeper zk;
	private String selfPath;
	private String waitPath;
	private String name;

	public DistributedLock(int sessionId) throws InterruptedException {
		this.name = "客户端名称：" + sessionId;
	}

	public boolean getLock() throws KeeperException, InterruptedException, IOException {
		connection();
		selfPath = zk.create(Constants.LOCK_SUB, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		if (checkMinPath()) {
			getLockSuccess();
			releaseConnection();
		}
		return true;
	}

	public void connection() throws InterruptedException, IOException {
		this.zk = new ZooKeeper(ZkConfig.getConnection(), ZkConfig.getTimeOut(), this);
	}

	public void releaseConnection() throws InterruptedException {
		if (this.zk != null) {
			conncedSemaphore.countDown();
			this.zk.close();
		}
		logger.info(name + "释放连接");
	}

	private void getLockSuccess() throws KeeperException, InterruptedException {
		if (zk.exists(this.selfPath, false) == null) {
			logger.error(name + "本节点已不在了");
			return;
		}
		logger.info(name + "获取锁成功,开始工作...");
		Thread.sleep(2000);
		logger.info(name + "获取锁成功,结束工作...");
		logger.info(name + "删除本节点：" + selfPath);
		zk.delete(this.selfPath, -1);
	}

	private boolean checkMinPath() throws KeeperException, InterruptedException {
		List<String> subNodes = zk.getChildren(Constants.LOCK_SUB, false);
		Collections.sort(subNodes);
		int index = subNodes.indexOf(selfPath.substring(Constants.LOCK_GROUP.length() + 1));
		switch (index) {
		case -1:
			logger.info(name + "," + selfPath + ",已不存在了");
			return false;
		case 0:
			logger.info(name + "," + selfPath + ",排在最前面");
			return true;
		default:
			this.waitPath = Constants.LOCK_GROUP + "/" + subNodes.get(index - 1);
			logger.info(name + "," + waitPath + ",排在我前面的是：" + waitPath);
			try {
				zk.getData(waitPath, false, new Stat());
			} catch (KeeperException e) {
				if (!exists(waitPath)) {
					logger.info(name + "子节点中，排在我前面的" + waitPath + "已失去联系了");
					return checkMinPath();
				} else {
					throw e;
				}
			}
			return false;
		}
	}

	private boolean exists(String nodePath) throws KeeperException, InterruptedException {
		if (zk.exists(nodePath, false) != null) {
			return true;
		}
		return false;
	}

	@Override
	public void process(WatchedEvent event) {
		logger.debug(event.toString());
		Event.KeeperState keeperState = event.getState();
		Event.EventType eventType = event.getType();
		if (Event.KeeperState.SyncConnected == keeperState) {
			if (Event.EventType.None == eventType) {
				logger.info(name + "成功连接上服务器");
			} else if (event.getType() == Event.EventType.NodeDeleted) {
				logger.info(name + "已经挂了");
			}
		} else if (Event.KeeperState.Disconnected == keeperState) {
			logger.info(name + "与服务器断开连接");
		} else if (Event.KeeperState.AuthFailed == keeperState) {
			logger.info(name + "权限检查失败");
		} else if (Event.KeeperState.Expired == keeperState) {
			logger.info(name + "会话失效");
		}
	}

}
