package com.example.demo.service;

import java.util.concurrent.*;

public class ThreadPool {

    public BlockingQueue blockingQueue = new ArrayBlockingQueue(10);

    public ThreadPoolExecutor pool = new ThreadPoolExecutor(3,6,50,TimeUnit.MILLISECONDS, blockingQueue);

    public void doExecutor(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        for (int i=0; i<1000000; i++){
            int count = 0;
        }
        System.out.println(pool.getPoolSize());
    }

    public static class ThreadTest extends Thread{
        @Override
        public void run(){
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        ThreadPool threadPool = new ThreadPool();
        threadPool.pool.execute(new ThreadTest());
        System.out.println(threadPool.pool.getPoolSize());
        threadPool.pool.execute(new ThreadTest());
        System.out.println(threadPool.pool.getPoolSize());
        threadPool.pool.execute(new ThreadTest());
        System.out.println(threadPool.pool.getPoolSize());
        while(threadPool.pool.getActiveCount() > 1){
            System.out.println("test");
        }
        System.out.println(threadPool.pool.getActiveCount());
        threadPool.doExecutor();
        System.out.println(threadPool.pool.getActiveCount());
        System.out.println(threadPool.pool.getPoolSize());
    }
}
