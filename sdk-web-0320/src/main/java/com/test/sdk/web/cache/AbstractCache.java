package com.test.sdk.web.cache;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public abstract class AbstractCache implements Runnable {
    protected boolean start = false;
    protected int interval = 20 * 1000 * 60;

    @Override
    public void run() {
        try {
            while (this.start) {
                System.out.println("更新缓存");
                update();
                Thread.sleep(interval);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @PostConstruct
    public void startCache() {
        this.start = true;
        new Thread(this).start();
    }


    @PreDestroy
    public void stop() {
        this.start = false;
    }

    public abstract void update();
}
