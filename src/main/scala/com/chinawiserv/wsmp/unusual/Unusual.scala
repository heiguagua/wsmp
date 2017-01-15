package com.chinawiserv.wsmp.unusual

import java.util.concurrent.ExecutorService
import com.chinawiserv.wsmp.handler.DataHandler
import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}
import com.chinawiserv.wsmp.unusual.mem.MemManager
import com.chinawiserv.wsmp.websocket.WSClient
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import scala.collection.JavaConversions._;

//@Component
class Unusual extends DataHandler with  InitializingBean{

  @Value("${websocket.host}")
  var endpointURI: String = _;
  private val tasksOfExecutor = 5;
  private var memManager: MemManager = _;
  private var wsClient: WSClient = _;
  private var executor: ExecutorService = _;

  override def afterPropertiesSet(): Unit = {
    this.memManager = new MemManager();
    this.wsClient = new WSClient(this.endpointURI);
    this.executor = ThreadPool.newThreadPool(12, new CustomThreadFactory("UnusualExecutor-"));
  }

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
