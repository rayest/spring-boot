package mobi.rayson.curatorclient;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorClient {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = buildClient();
        client.start();
        createNode(client, "/zk-curator/c1");
        System.out.println(getData(client, "/zk-curator/c1"));
    }

    private static CuratorFramework buildClient(){
        return CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
    }

    private static void createNode(CuratorFramework client, String path) throws Exception {
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path, "init".getBytes());
    }

    private static void deleteNode(CuratorFramework client, String path) throws Exception {
        Stat stat = new Stat();
        client.delete()
                .deletingChildrenIfNeeded()
                .withVersion(stat.getVersion())
                .forPath(path);
    }

    private static String getData(CuratorFramework client, String path) throws Exception {
        Stat stat = new Stat();
        return new String(client.getData().storingStatIn(stat).forPath(path));
    }
}
