package com.chinawiserv.wsmp.unusual

import java.util.concurrent._

import com.chinawiserv.wsmp.mem.MemManager
import com.chinawiserv.wsmp.spark.model.Cmd
import com.chinawiserv.wsmp.unusual.handler.{CustomThreadFactory, CustomWorker}
import scala.collection.mutable.ListBuffer

class Unusual {

  private val tasksOfExecutor = 20;
  private val memManager = new MemManager();
  private val executor = newThreadPool(60, new CustomThreadFactory());

  def compute(cmds : List[Cmd]): Unit = {
    if (cmds != null && !cmds.isEmpty) {
      val list = cmds.sliding(tasksOfExecutor, tasksOfExecutor);
      list.foreach(x => {
        executor.execute(new CustomWorker(memManager, x));
      });
    }
  }

  def compute(cmds : java.util.List[Cmd]): Unit = {
    if (cmds != null && !cmds.isEmpty) {
      val list = new ListBuffer[Cmd];
      for (i <- 0.until(cmds.size())) {
        list += cmds.get(i);
      }
      this.compute(list.toList);
    }
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
