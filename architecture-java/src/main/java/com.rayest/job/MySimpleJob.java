package com.rayest.job;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("Thread ID: {}, 作业分片总数: {}, 当前分片项: {}. 当前参数: {}, 作业名称: {}.作业自定义参数: {}",
                Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter());

        log.info("处理业务");

        switch (shardingContext.getShardingItem()) {
            case 0:
                log.info("分片一处理...");
                break;
            case 1:
                log.info("分片二处理...");
                break;
            case 2:
                log.info("分片三处理...");
                break;
        }
    }
}
