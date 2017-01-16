package com.chinawiserv

import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}

object AppTestScala {

  def main(args: Array[String]): Unit = {
    val executor = ThreadPool.newThreadPool(12, new CustomThreadFactory("AExecutor-"));
    for (i <- 0.to(1000000)) {
      executor.execute(new AExecutor());
    }
  }

  class AExecutor extends Runnable {
    override def run(): Unit = {
      println("this.hashCode()="+this.hashCode());
      Thread.sleep(500);
    }
  }

}
