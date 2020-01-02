package com.executorservice.test.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ShutdownTest {
    public static ExecutorService pool = new ThreadPoolExecutor(10, 100, 2, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(DemoApplication.class);
        try {
            List<Integer> list = new ArrayList<>();
            for(int i=0;i<200;i++){
                list.add(i);
            }
            for(Integer intteger: list){
                pool.submit(()->{
                    try {
                        Thread.sleep(10000);
                        System.out.println(intteger);
                        Thread.sleep(10000);
                    }catch (Exception ex){
                        logger.error("出现异常了",ex);
                    }

                });
            }
            //pool.shutdown();
        }catch (Exception ex){
            logger.error(" shutdown 之后是否异常",ex);
        }
        logger.info("数据完了");
    }

}
