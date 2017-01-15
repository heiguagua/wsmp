package com.chinawiserv.wsmp.statistics;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DataFlow implements Serializable {

    private double avgVal;
    private AtomicInteger drc;
    private AtomicLong totalVal;
    private ConcurrentHashMap<String, Integer> map;
    private static ScheduledExecutorService scheduler;
    private DecimalFormat df = new DecimalFormat("#.00");

    public long inc(int amount) {
        this.drc.addAndGet(amount);
        return this.totalVal.addAndGet(amount);
    }

    public DataFlow() {
        this.avgVal = 0.0;
        this.drc = new AtomicInteger(0);
        this.totalVal = new AtomicLong(0);
        this.map = new ConcurrentHashMap<String, Integer>(10);
        this.scheduler =  Executors.newScheduledThreadPool(1);
        this.scheduler.scheduleAtFixedRate(new StatisticsRunnable(), 1, 1, TimeUnit.SECONDS);
    }

    public long inc() {
        this.drc.incrementAndGet();
        return this.totalVal.incrementAndGet();
    }

    public double getAvgVal() {
        return this.avgVal;
    }

    public long getTotalVal() {
        return this.totalVal.get();
    }

    private void statistics() {
        String now = String.valueOf(System.currentTimeMillis() / 1000);
        now = now.substring(now.length() - 1, now.length());
        this.map.put(now, drc.get());
        this.drc.set(0);
        Collection<Integer> collection = map.values();
        double _avgVal = 0.0;
        for (int val : collection) {
            _avgVal += val;
        }
        this.avgVal = Double.valueOf(this.df.format(_avgVal / collection.size()));
    }

    private class StatisticsRunnable implements Runnable, Serializable {
        public void run() {
            statistics();
        }
    }

}
