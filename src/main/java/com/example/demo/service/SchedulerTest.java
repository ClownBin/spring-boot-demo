package com.example.demo.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulerTest {

    public static ThreadPool threadPool = new ThreadPool();

    @Scheduled(fixedRate = 1000 * 1000)
    public void testThread(){
        soutPoolActiveCount();
        if(threadPool.pool.getActiveCount() < 3){
            threadPool.pool.execute(new ThreadPool.ThreadTest());
            System.out.println("new Thread");
        } else {
            System.out.println("thread is up to max num.");
        }
        soutPoolActiveCount();
    }

    public void soutPoolActiveCount(){
        System.out.println(threadPool.pool.getActiveCount());
    }
}
