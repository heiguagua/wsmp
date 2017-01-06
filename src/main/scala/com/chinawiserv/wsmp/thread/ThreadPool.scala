package com.chinawiserv.wsmp.thread

import java.util.concurrent._

object ThreadPool {

  def newThreadPool(nThreads: Int, threadFactory: ThreadFactory): ExecutorService = {
    new ThreadPoolExecutor(0,
                           nThreads,
                           60L,
                           TimeUnit.SECONDS,
                           new SynchronousQueue[Runnable](),
                           threadFactory);
  }

}
