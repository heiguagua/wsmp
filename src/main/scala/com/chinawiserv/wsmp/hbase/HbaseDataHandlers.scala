package com.chinawiserv.wsmp.hbase

import java.util.UUID

import com.chinawiserv.wsmp.handler.DataHandler
import com.chinawiserv.wsmp.model.Cmd
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HColumnDescriptor, HTableDescriptor, TableName}
import org.apache.hadoop.hbase.client.{Admin, ConnectionFactory, Put, Table}
import org.apache.hadoop.hbase.util.Bytes._
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

/**
  * Created by chinawiserv-0006 on 2017/1/11.
  */
@Component
class HbaseDataHandlers extends DataHandler with InitializingBean {

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  @Autowired
  var configuration: Configuration = _;

  @Value("${hbase.tableName}")
  var hbaseTableName: String = _;

  lazy val connection = newConnection();

  override def afterPropertiesSet(): Unit = {

    AutoClose.using(this.connection.getAdmin, (admin: Admin) => {
      val tableName = TableName.valueOf(hbaseTableName);
      if (!admin.tableExists(tableName)) {
        logger.info("Hbase table {} not exists, now will create it", this.hbaseTableName)
        val tableDetailInfo = new HTableDescriptor(tableName);
        tableDetailInfo.addFamily(new HColumnDescriptor(toBytes("default")))
        admin.createTable(tableDetailInfo);
        logger.info("Hbase table {} create success !", this.hbaseTableName)
      }
    })
  }

  @Async
  override def compute(cmds: java.util.List[Cmd]): Unit = {

    AutoClose.using(this.connection.getTable(TableName.valueOf("TestRadio")), (table: Table) => {
      table.put(
        cmds.map(cmd => {
          val uuid = UUID.randomUUID().toString;
          val rowid = toBytes(uuid);
          val put = new Put(rowid);
          val family = toBytes("default");
          cmd.getClass.getDeclaredFields.foreach(field => {
            field.setAccessible(true);
            val qualifier = toBytes(field.getName);
            val value = getBytes(field.get(cmd));
            put.addColumn(family, qualifier, value);
          });
          put
        }
        )
      )
    });

  }

  def getBytes[T](obj: T): Array[Byte] = {
    obj match {
      case obj: String => toBytes(obj)
      case obj: Long => toBytes(obj)
      case obj: Short => toBytes(obj)
      case obj: Int => toBytes(obj)
      case obj: ArrayBuffer[_] => toBytes(obj.addString(new StringBuilder, ",").toString)
    }
  }


  def newConnection() = ConnectionFactory.createConnection(configuration);

}
