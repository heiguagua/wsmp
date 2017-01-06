package com.chinawiserv.wsmp.unusual.handler

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

class CustomThreadFactory extends ThreadFactory{

  private val atomicInteger: AtomicInteger = new AtomicInteger(0);

  def newThread(runnable: Runnable): Thread = {
    atomicInteger.incrementAndGet();
    new CustomThread(runnable, atomicInteger);
  }

}
