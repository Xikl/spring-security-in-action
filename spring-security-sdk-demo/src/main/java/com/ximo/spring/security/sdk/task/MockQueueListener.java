package com.ximo.spring.security.sdk.task;

import com.ximo.spring.security.sdk.constants.AsyncConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
@Slf4j
@Component
public class MockQueueListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    /**
     * spring boot不会出现二次调用问题
     *
     * @param contextRefreshedEvent contextRefreshedEvent 百度一下
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // 项目启动的时候创建一个线程
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(AsyncConstants.MOCK_QUEUE_THREAD_SIZE,
                new BasicThreadFactory.Builder().namingPattern("mock-queue-pool-%d").daemon(true).build());

        //执行操作
        executorService.scheduleWithFixedDelay(this::setDeferredResult, AsyncConstants.MOCK_QUEUE_INITIAL_DELAY, AsyncConstants.MOCK_QUEUE_DELAY, TimeUnit.SECONDS);
    }


    /**
     *
     * 设置该订单的deferredResult返回值
     */
    private void setDeferredResult() {
        try {
            String completeOrder = mockQueue.getCompleteOrder();
            if (StringUtils.isNotBlank(completeOrder)) {
                deferredResultHolder.take(completeOrder)
                        .setResult("place order success");
                mockQueue.setCompleteOrder(null);
            }
        } catch (Exception e) {
            log.info("【mock listener listener】出现错误", e);
        }
    }
}
