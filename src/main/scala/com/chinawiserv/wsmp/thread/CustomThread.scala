package com.chinawiserv.wsmp.thread

import java.util.concurrent.atomic.AtomicInteger

class CustomThread extends Thread {

  private var target: Runnable = null
  private var counter: AtomicInteger = null

  def this(target: Runnable, counter: AtomicInteger, threadNamePrefix: String) {
    this();
    this.target = target;
    this.counter = counter;
    if (threadNamePrefix != null) {
      this.setName(threadNamePrefix + counter.get);
    }
    else {
      this.setName(counter.get.toString);
    }
  }

  override def run() {
    try {
      target.run();
    }
    finally {
      counter.getAndDecrement
    }
  }

}
