package mobi.rayson.demo2;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Main {
    public static void main(String[] args) {
        Main m1 = new Main();
        Main m2 = new Main();
        System.out.println(m1.equals(m2));
    }
}
