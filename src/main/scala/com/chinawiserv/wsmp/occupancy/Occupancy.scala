package com.chinawiserv.wsmp.occupancy

import java.util.concurrent.Semaphore

import com.chinawiserv.model.Cmd
import com.chinawiserv.wsmp.handler.DataHandler
import com.chinawiserv.wsmp.occupancy.store.mem._

import scala.collection.JavaConversions._

class Occupancy extends DataHandler {

  @throws[Exception]
  def compute(cmds: java.util.List[Cmd], semaphore: Semaphore): Unit = {
    if (cmds != null && !cmds.isEmpty) {
      FlushMem.offer(cmds.toList);
    }
  }

}
