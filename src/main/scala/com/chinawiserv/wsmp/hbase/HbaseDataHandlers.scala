package com.chinawiserv.wsmp.hbase

import com.chinawiserv.wsmp.handler.DataHandler
import com.chinawiserv.wsmp.model.Cmd
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.{Admin, ConnectionFactory, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

import scala.collection.JavaConversions._

/**
 * Created by chinawiserv-0006 on 2017/1/11.
 */
//@Component
class HbaseDataHandlers extends DataHandler{

  @Autowired
  var configuration : Configuration = _;

  lazy val connection = newConnection();

  @Async
  override def compute(cmds: java.util.List[Cmd]): Unit = {
    printf("hbase recive cmds %d", cmds.size());
    AutoClose.using(this.connection.getAdmin, (admin : Admin) => {
          cmds.map((cmd : Cmd) =>{
            var rowid = Bytes.va
              var put = new Put();

            put
          })
    });
  }

  def newConnection()  = ConnectionFactory.createConnection(configuration);
}
