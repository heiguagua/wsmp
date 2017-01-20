package com.chinawiserv.wsmp
package unusual;

import java.text.DecimalFormat
import java.util

import com.chinawiserv.model.Cmd
import com.chinawiserv.wsmp.operator.Operator
import com.chinawiserv.wsmp.unusual.mem.{Mem, MemManager}
import com.mongodb.client.model.{Aggregates, BsonField}
import org.bson.Document
import org.bson.conversions.Bson
import org.slf4j.LoggerFactory

class UnusualExecutor(val cmds : List[Cmd], val memManager: MemManager) extends Runnable {

  private val log = LoggerFactory.getLogger(classOf[UnusualExecutor]);

  private val mongoColNamePrefix = "Unusual";
  private val df = new DecimalFormat("#.000")

  override def run(): Unit = {
    if (cmds != null && cmds.length > 0) {
      mongoDB.shardCollection(mongoColNamePrefix+"Levels", new Document("id", 1));
      cmds.foreach(cmd => {
        val current = Operator.toMem(cmd);
        val history = this.readAndSaveData(cmd);
        this.computeUnusual(current, history);
      });
    }
  }

  /**
    * 数据当前节点的前 10 条数据，并保存当前数据（保存到内存与redis）
    * @param cmd
    * @return
    */
  private def readAndSaveData(cmd: Cmd): List[Array[Byte]] = {
    val history = memManager.readDataFromMem(cmd.getId());
    memManager.saveDataToMem(cmd);
    return history;
  }

  /**
    * 调用异动算法接口
    * @param current 当前数据
    * @param history 10 条历史数据
    */
  private def computeUnusual(current: Mem, history: List[Array[Byte]]): Unit = {
    val res = this.doCompute(current.numOfTraceItems.toInt, current.levels, history);
    this.analyzeUnusual(current, res);
  }

  /**
    * 执行实时异动计算
    * @param numOfTraceItems 频率数量
    * @param current 当前消息
    * @param history 10条历史消息
    * @return 异动计算结果
    */
  def doCompute(numOfTraceItems: Int, current: Array[Byte], history: List[Array[Byte]]): Array[Byte] = {
    val unusualLevel = new UnusualLevel(numOfTraceItems);
    unusualLevel.CalcUnusualLevel(current, null, numOfTraceItems);
    history.foreach(x => {
      unusualLevel.CalcUnusualLevel(x, null, numOfTraceItems);
    });
    val res = unusualLevel.Res;
    unusualLevel.FreeOccObj();
    return res;
  }

  /**
    * 根据当前消息的异动计算结果进行分析，入库
    * @param current  当前消息
    * @param res 当前消息的异动计算结果
    */
  private def analyzeUnusual(current: Mem, res: Array[Byte]): Unit = {
    if (current != null && res != null) {
      val amount = res.count(_ == 1);
      //amount > 0 表示该消息中至少存在一个频率点有异动情况
      if (amount > 0) {
        val docs = new util.ArrayList[Document]();
        for (i <- 0.until(res.length)) {
          if (res(i) == 1) {
            val currentFreq = (20 + (0.025 * i)) * 1000;
            val currentLevel = current.levels(i);
            val currentDate = current.scanOverTime * 1000;
            val doc = new Document();
            doc.put("freq", currentFreq.toInt);
            doc.put("level", currentLevel);
            doc.put("dt", currentDate);
            docs.add(doc);
            log.info("收到异动数据，基站编号: "+current.id+", 异动频率: "+df.format(currentFreq / 1000)+", 能量值: "+currentLevel);
          }
        }
        mongoDB.shardCollection(mongoColNamePrefix+current.id, new Document("freq", 1));
        mongoDB.mc.insert(mongoDB.dbName, mongoColNamePrefix+current.id, docs, null);
        val amount = this.countByColName(mongoColNamePrefix+current.id);
        if (amount > 0) {
          val doc = new Document();
          doc.put("id", current.id);
          doc.put("un", amount);
          doc.put("flat", current.flat);
          doc.put("flon", current.flon);
          mongoDB.mc.insert(mongoDB.dbName, mongoColNamePrefix+"Levels", doc, null);
          memManager.saveDataToWeb(current.id, amount);
        }
      }
    }
  }

  /**
    * 统计指定集合的数据量
    * @param colName 集合名
    * @return 指定集合的数据量
    */
  def countByColName(colName: String): Int = {
    val pipeline: util.List[Bson] = new util.ArrayList[Bson];
    pipeline.add(Aggregates.group(new Document("_id", "$freq")))
    val list: util.List[Document] = mongoDB.mc.aggregate(mongoDB.dbName, colName, pipeline)
    if (list != null && !list.isEmpty) {
      list.size();
    }
    else {
      0;
    }
  }

  def countByColName_BAK(colName: String): Int = {
    val pipeline: util.List[Bson] = new util.ArrayList[Bson];
    pipeline.add(Aggregates.group(null, new BsonField("count", new Document("$sum", 1))))
    pipeline.add(Aggregates.project(new Document("_id", 0)))
    val list: util.List[Document] = mongoDB.mc.aggregate(mongoDB.dbName, colName, pipeline)
    if (list != null && !list.isEmpty) {
      list.get(0).get("count").toString.toInt;
    }
    else {
      0;
    }
  }




}
