package com.ximo.spring.security.sdk.controller.async;

import com.ximo.spring.security.sdk.core.vo.ResultVO;
import com.ximo.spring.security.sdk.task.DeferredResultHolder;
import com.ximo.spring.security.sdk.task.MockQueue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
@Slf4j
@RestController
public class AsyncController {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @PostMapping("/order")

    public Callable<ResultVO> order() {
        log.info("主线程开始");
        Callable<ResultVO> callable = () -> {
            log.info("副线程开始");
            Thread.sleep(1000);
            log.info("副线程返回");
            return ResultVO.success();
        };
        log.info("主线程结束");
        return callable;
    }

    @PostMapping("/order-async")
    public DeferredResult<String> orderAsync() {
        log.info("主线程开始");
        String orderNumber = RandomStringUtils.randomNumeric(8);
        //存入模拟队列中
        mockQueue.setPlaceOrder(orderNumber);

        //直接返回改结果
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.put(orderNumber, result);
        log.info("主线程结束");
        return result;
    }
}
