package com.executorservice.test.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingExecutor {
    //  LinkedBlockingQueue 线程是无界的,最大是2的32次方-1
    // 设置的超时时间没有作用
    public static ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 10, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy());
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(DemoApplication.class);
        try {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 200; i++) {
                list.add(i);
            }
            for (Integer intteger : list) {
                pool.submit(() -> {
                    try {
                        Thread.sleep(5000);
                        System.out.println(intteger);
                        Thread.sleep(1000);
                    } catch (Exception ex) {
                        logger.error("出现异常了", ex);
                    }

                });
            }


            logger.info("数据完了");
            while (true) {
                Thread.sleep(1000);
                System.out.println("线程池中线程数目：" + pool.getPoolSize() + "，队列中等待执行的任务数目：" +
                        pool.getQueue().size() + "，已执行完的任务数目：" + pool.getCompletedTaskCount());
            }
        }
        catch (Exception ex){
            logger.error(" shutdown 之后是否异常",ex);
        }
    }
}