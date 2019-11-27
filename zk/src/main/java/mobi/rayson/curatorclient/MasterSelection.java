package mobi.rayson.curatorclient;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

public class MasterSelection {
    public static void main(String[] args) throws InterruptedException {
        CuratorFramework client = CuratorClient.buildClient();
        client.start();

        String path = "/curator-recipes-master-path";
        selectAsMaster(client, path);
    }

    private static void selectAsMaster(CuratorFramework client, String path) throws InterruptedException {
        LeaderSelector selector = new LeaderSelector(client, path, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("成为 master 角色");
                Thread.sleep(5000);
                System.out.println("完成 master 操作，释放 master 权利");
            }
        });

        selector.autoRequeue();
        selector.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
