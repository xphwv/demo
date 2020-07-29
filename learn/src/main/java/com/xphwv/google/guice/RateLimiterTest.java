package com.xphwv.google.guice;

import com.google.common.util.concurrent.RateLimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by xupan on 2018-12-21
 */
public class RateLimiterTest {
    private static final RateLimiter rateLimiter = RateLimiter.create(2.0); // rate is "2 permits per second"
    private static final Executor executor = Executors.newCachedThreadPool();
    private static void submitTasks(List<Runnable> tasks, Executor executor) {
        for (Runnable task : tasks) {
            rateLimiter.acquire(); // may wait
            executor.execute(task);
        }
    }
    public static void main(String[] args) {
        List<Runnable> tasks=new ArrayList<Runnable>(5);
        for (int i=0;i<5;i++){

            tasks.add(new Runnable() {
                public void run() {
                    System.out.println(System.currentTimeMillis());
                }
            });
        }
        submitTasks(tasks,executor);
    }
}
