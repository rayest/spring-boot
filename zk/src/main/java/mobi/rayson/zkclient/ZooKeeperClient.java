package mobi.rayson.zkclient;

import mobi.rayson.StringCallbackImpl;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZooKeeperClient implements Watcher {
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new ZooKeeperClient());
        System.out.println(zooKeeper.getState());

        latch.await();

        // 同步创建持久化的节点
        String path1 = zooKeeper.create("/zk-java-demo-sync", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("znode 创建成功: " + path1);

        // 异步创建持久化的节点
        zooKeeper.create(
                "/zk-java-demo-async",
                "hello world".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT,
                new StringCallbackImpl(), "I am context");


        System.out.println("zk session 已建立");
    }

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            latch.countDown();
        }
    }
}

