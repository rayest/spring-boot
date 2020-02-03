package com.rayest.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

import java.io.IOException;

@Slf4j
@RocketMQTransactionListener(txProducerGroup = "arch-test-tx-group")
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.info("执行本地事务...");
        log.info("message:{}", message);
        log.info("object:{}", o);
        try {
            doLocal();
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    private void doLocal() {
        // 执行本地事务业务 xxxService.doSomething();
        int localResult = 1 / 0;
        log.info("localResult: {}", localResult);
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        log.info("检查本地事务...");
        log.info("message:{}", message);
        // Boolean result = xxxService.isSuccess(msg,arg);
        Boolean result = true;
        if (result != null) {
            if (result) {
                return RocketMQLocalTransactionState.COMMIT;
            } else {
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        }
        return RocketMQLocalTransactionState.UNKNOWN;

    }
}
