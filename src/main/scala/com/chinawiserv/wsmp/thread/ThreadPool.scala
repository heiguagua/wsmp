package com.chinawiserv.wsmp.thread

import java.util.concurrent._

object ThreadPool {

  def newThreadPool(nThreads: Int, threadFactory: ThreadFactory): ThreadPoolExecutor = {
    val tpe = new ThreadPoolExecutor(nThreads,
                                      nThreads,
                                      60L,
                                      TimeUnit.SECONDS,
                                      new LinkedBlockingQueue[Runnable](),
                                      threadFactory);
    tpe.allowCoreThreadTimeOut(true);
    return tpe;
  }

}
