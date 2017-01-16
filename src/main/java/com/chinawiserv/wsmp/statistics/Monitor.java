package com.chinawiserv.wsmp.statistics;

public class Monitor {

    public static String getInfo() {
        Runtime runtime =  Runtime.getRuntime();

        // 可用内存
        long totalMemory = runtime.totalMemory() / 1024 / 1024;

        // 剩余内存
        long freeMemory = runtime.freeMemory() / 1024 / 1024;

        // 最大内存
        long maxMemory = runtime.maxMemory() / 1024 / 1024 ;
        return  "可用内存:" + totalMemory + " MB, 剩余内存:"+freeMemory+" MB, 最大内存:"+maxMemory+" MB";
    }

}
