package com.chinawiserv.wsmp.unusual

import java.util.concurrent.Executors

import com.chinawiserv.wsmp.mem.MemManager
import com.chinawiserv.wsmp.mem.model.Cmd
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}

import scala.collection.mutable.ListBuffer

class Unusual {

  private val tasksOfExecutor = 5;
  private val memManager = new MemManager();
  private val executor = ThreadPool.newThreadPool(60, new CustomThreadFactory("UnusualExecutor-"));

  def compute(cmds : List[Cmd]): Unit = {
    if (cmds != null && !cmds.isEmpty) {
      val list = cmds.sliding(tasksOfExecutor, tasksOfExecutor);
      list.foreach(x => {
        executor.execute(new UnusualExecutor(memManager, x));
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

}
