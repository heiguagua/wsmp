package com.chinawiserv.wsmp.unusual

import java.util.concurrent._
import com.chinawiserv.wsmp.mem.MemManager
import com.chinawiserv.wsmp.spark.model.Cmd
import com.chinawiserv.wsmp.unusual.handler.{CustomThreadFactory, CustomWorker}

class Unusual {

  private val tasksOfExecutor = 20;
  private val memManager = new MemManager();
  private val executor = newThreadPool(60, new CustomThreadFactory());

  def compute(cmds : List[Cmd]): Unit = {
    val list = cmds.sliding(tasksOfExecutor, tasksOfExecutor);
    list.foreach(x => {
      executor.execute(new CustomWorker(memManager, x));
    });
  }

  def newThreadPool(nThreads: Int, threadFactory: ThreadFactory): ExecutorService = {
    new ThreadPoolExecutor(0,
                           nThreads,
                           60L,
                           TimeUnit.SECONDS,
                           new SynchronousQueue[Runnable](),
                           threadFactory);
  }

}
