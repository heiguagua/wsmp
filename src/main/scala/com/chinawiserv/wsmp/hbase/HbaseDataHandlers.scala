package com.chinawiserv.wsmp.hbase

import java.lang.reflect.Field
import java.util.UUID

import com.chinawiserv.model.Cmd
import com.chinawiserv.util.FstUtil
import com.chinawiserv.wsmp.handler.DataHandler
import com.chinawiserv.wsmp.hbase.AutoClose._
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes._
import org.apache.hadoop.hbase.{HColumnDescriptor, HTableDescriptor, TableName}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.scheduling.annotation.Async

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

class HbaseDataHandlers extends DataHandler with InitializingBean {

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  @Autowired
  var configuration: Configuration = _;

  @Value("${hbase.tableName}")
  var hbaseTableName: String = _;
  var tableName : TableName = _;
  var fieldNames : Array[Field] = _;
  var mutator : BufferedMutator = _;


  lazy val connection: Connection = newConnection();

  override def afterPropertiesSet(): Unit = {

    this.tableName = TableName.valueOf(this.hbaseTableName);
    val params = new BufferedMutatorParams(tableName);
    params.writeBufferSize(1024 * 1024)
    this.mutator = this.connection.getBufferedMutator(params);

    using(this.connection.getAdmin, (admin: Admin) => {
      if (!admin.tableExists(tableName)) {
        logger.info("Hbase table {} not exists, now will create it", this.hbaseTableName)
        val tableDetailInfo = new HTableDescriptor(tableName);
        tableDetailInfo.addFamily(new HColumnDescriptor(toBytes("default")))
        admin.createTable(tableDetailInfo);
        logger.info("Hbase table {} create success !", this.hbaseTableName)
      }
    })
    this.fieldNames = classOf[Cmd].getDeclaredFields;
    this.fieldNames.foreach(f => f.setAccessible(true));
  }

  @Async
  override def compute(cmds: java.util.List[Cmd]): Unit = {

    mutator.mutate(cmds.map(cmd => {

      val uuid = UUID.randomUUID().toString;
      val rowid = toBytes(uuid);
      val put = new Put(rowid);
      val family = toBytes("default");

      this.fieldNames.foreach((field: Field) => {
        val qualifier = toBytes(field.getName);
        val value = getBytes(field.get(cmd));
        put.addColumn(family, qualifier, value);
        put.setDurability(Durability.SKIP_WAL)
      });
      put;
    }));
//    mutator.flush();
    println("hbase 入库条数:" + cmds.size());

  }

  def getBytes[T](obj: T): Array[Byte] = obj match {
    case obj: String => toBytes(obj)
    case obj: Long => toBytes(obj)
    case obj: Double => toBytes(obj)
    case obj: Float => toBytes(obj)
    case obj: Short => toBytes(obj)
    case obj: Int => toBytes(obj)
    case obj: ArrayBuffer[_] => toBytes(obj.addString(new StringBuilder, ",").toString)
    case obj => FstUtil.fst.asByteArray(obj);
  }

  def newConnection(): Connection = ConnectionFactory.createConnection(configuration);

}
