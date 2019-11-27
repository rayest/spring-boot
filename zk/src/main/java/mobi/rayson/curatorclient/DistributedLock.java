package mobi.rayson.curatorclient;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class DistributedLock {
    public static void main(String[] args) {
        String path = "/distributed-lock";
        CuratorFramework client = CuratorClient.buildClient();
        client.start();

        InterProcessLock lock = new InterProcessMutex(client, path);
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                    lock.acquire();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss|SSS");
                String orderNo = simpleDateFormat.format(new Date());
                System.out.println("订单号：" + orderNo);
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        }
        latch.countDown();
    }
}
