package com.ximo.spring.security.sdk.task;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
@Component
@Data
@Slf4j
public class MockQueue {

    private String placeOrder;

    private String completeOrder;

    public void setPlaceOrder(String placeOrder)  {
        //设置线程名
        ThreadFactory orderThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("order-pool-%d").build();

        ExecutorService orderExecutorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), orderThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        orderExecutorService.execute(this::execPlaceOrder);
        orderExecutorService.shutdown();
    }

    private void execPlaceOrder() {
        try {
            log.info("接到下单请求");
            Thread.sleep(1000);
            //假设completeOrder有值则下单已经完成
            this.completeOrder = placeOrder;
            log.info("下单请求处理完毕， placeOrder：{}", placeOrder);
        } catch (InterruptedException e) {
            log.error("【模拟下单请求】下单请求被中断，订单号为", placeOrder);
        }
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
