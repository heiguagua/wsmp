package com.chinawiserv.wsmp.unusual.handler

import java.util.concurrent.atomic.AtomicInteger

class CustomThread extends Thread {

  private var target: Runnable = null
  private var counter: AtomicInteger = null

  def this(target: Runnable, counter: AtomicInteger) {
    this();
    this.target = target;
    this.counter = counter;
    this.setName("TN-" + counter.get);
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
