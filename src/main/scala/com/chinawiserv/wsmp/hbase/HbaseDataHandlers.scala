package com.chinawiserv.wsmp.hbase

import java.util
import com.chinawiserv.wsmp.handler.DataHandler
import com.chinawiserv.wsmp.model.Cmd

//@Component
class HbaseDataHandlers extends DataHandler{

  override def compute(cmds: util.List[Cmd]): Unit = {
      printf("hbase recive cmds %d", cmds.size());
  }
}
