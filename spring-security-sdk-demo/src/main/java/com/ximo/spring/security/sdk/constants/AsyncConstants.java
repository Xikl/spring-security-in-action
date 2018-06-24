package com.ximo.spring.security.sdk.constants;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
public interface AsyncConstants {

    /** 模拟请求队列中的线程大小 */
    Integer MOCK_QUEUE_THREAD_SIZE = 1;

    /** 初始化延迟 */
    long MOCK_QUEUE_INITIAL_DELAY = 5 * 60;

    /** 下次调用时间 */
    long MOCK_QUEUE_DELAY = 10;
}
