package com.chinawiserv.wsmp.thread

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

class CustomThreadFactory(val threadNamePrefix: String = "") extends ThreadFactory{

  private val atomicInteger: AtomicInteger = new AtomicInteger(0);

  def newThread(runnable: Runnable): Thread = {
    atomicInteger.incrementAndGet();
    new CustomThread(runnable, atomicInteger, threadNamePrefix);
  }

}
