package mobi.rayson;

import org.apache.zookeeper.AsyncCallback;

public class StringCallbackImpl implements AsyncCallback.StringCallback {
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("异步创建节点结果。rc: " + rc + ", path: " + path + ", ctx: " + ctx + ", name: " + name);
    }
}
