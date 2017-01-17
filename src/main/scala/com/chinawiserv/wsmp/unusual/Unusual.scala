package com.chinawiserv.wsmp.unusual

import java.util.concurrent._
import com.chinawiserv.model.Cmd
import com.chinawiserv.wsmp.handler.DataHandler
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}
import com.chinawiserv.wsmp.unusual.mem.MemManager
import com.chinawiserv.wsmp.websocket.WSClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import scala.collection.JavaConversions._;

//@Component
class Unusual extends DataHandler with  InitializingBean{
  private val log = LoggerFactory.getLogger(classOf[Unusual]);

  @Value("${websocket.host}")
  var endpointURI: String = _;
  private var memManager: MemManager = _;
  private var wsClient: WSClient = _;
  private var executor: ThreadPoolExecutor = _;
  private var scheduler: ScheduledExecutorService = _;

  override def afterPropertiesSet(): Unit = {
    this.memManager = new MemManager();
    this.wsClient = new WSClient(this.endpointURI);
    this.executor = ThreadPool.newThreadPool(12, new CustomThreadFactory("UnusualExecutor-"));
    this.scheduler =  Executors.newScheduledThreadPool(1);
    this.scheduler.scheduleWithFixedDelay(new SendDataRunnable(), 1, 1, TimeUnit.SECONDS);
  }

  def compute(cmds : java.util.List[Cmd]): Unit = {
    compute(cmds.toList);
  }

  def compute(cmds : List[Cmd]): Unit = {
    try {
      if (cmds != null && !cmds.isEmpty) {
        val list = cmds.sliding(5, 5);
        list.foreach(shard => {
          executor.execute(new UnusualExecutor(shard, memManager));
        });
      }
    }
    catch {
      case e: Exception => log.error("Unusual.compute: "+e.getMessage) ;
    }
  }

  class SendDataRunnable extends Runnable with Serializable {
    override def run(): Unit = {
      try {
        val json = memManager.readDataFromWeb();
        if (json != null && !"".equals(json)) {
          wsClient.sendMessage(json);
        }
      }
      catch {
        case e: Exception => log.error("SendDataRunnable.run: "+e.getMessage);
      }
    }
  }

}
