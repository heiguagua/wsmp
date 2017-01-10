package com.chinawiserv.wsmp.unusual

import com.chinawiserv.wsmp.handler.DataHandler
import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}
import com.chinawiserv.wsmp.unusual.mem.MemManager
import scala.collection.JavaConversions._;

class Unusual extends DataHandler {

  private val tasksOfExecutor = 5;
  private val memManager = new MemManager();
  private val executor = ThreadPool.newThreadPool(60, new CustomThreadFactory("UnusualExecutor-"));

  @throws[Exception]
  def compute(cmds : java.util.List[Cmd]): Unit = {
    compute(cmds.toList);
  }

  def compute(cmds : List[Cmd]): Unit = {
    if (cmds != null && !cmds.isEmpty) {
      val list = cmds.sliding(tasksOfExecutor, tasksOfExecutor);
      list.foreach(x => {
        executor.execute(new UnusualExecutor(memManager, x));
      });
    }
  }

}
