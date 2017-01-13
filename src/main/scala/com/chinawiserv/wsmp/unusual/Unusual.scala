package com.chinawiserv.wsmp.unusual

import com.chinawiserv.wsmp.handler.DataHandler
import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}
import com.chinawiserv.wsmp.unusual.mem.MemManager
import com.chinawiserv.wsmp.websocket.WSClient
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Component

import scala.collection.JavaConversions._;

@Component
class Unusual extends DataHandler {

  private val tasksOfExecutor = 5;
  @Autowired
  var memManager: MemManager = _;
  @Autowired
  var wsClient: WSClient = _;
  private val executor = ThreadPool.newThreadPool(6, new CustomThreadFactory("UnusualExecutor-"));;

  @throws[Exception]
  def compute(cmds : java.util.List[Cmd]): Unit = {
    compute(cmds.toList);
  }

  def compute(cmds : List[Cmd]): Unit = {
    try {
      if (cmds != null && !cmds.isEmpty) {
        val list = cmds.sliding(tasksOfExecutor, tasksOfExecutor);
        list.foreach(shard => {
          executor.execute(new UnusualExecutor(shard, wsClient, memManager));
        });
      }
    }
    catch {
      case e: Exception => println("Unusual.compute: "+e.getMessage) ;
    }
  }

}
