package com.chinawiserv.wsmp.occupancy

import com.chinawiserv.wsmp.handler.DataHandler
import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.mongodb.MongoDB
import com.chinawiserv.wsmp.occupancy.store.mem._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.JavaConversions._

/**
  * Created by zengpzh on 2017/1/6.
  */
@Component
class Occupancy extends DataHandler {

  @Autowired
  val mongoDB: MongoDB = _;

  @throws[Exception]
  def compute(cmds: java.util.List[Cmd]): Unit = {
    if (cmds != null && !cmds.isEmpty) {
      FlushMem.offer(cmds.toList);
    }
  }

}
