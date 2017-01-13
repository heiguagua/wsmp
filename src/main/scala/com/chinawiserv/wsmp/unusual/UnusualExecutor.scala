package com.chinawiserv.wsmp.unusual

import java.util
import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.mongodb.MongoDB
import com.chinawiserv.wsmp.operator.Operator
import com.chinawiserv.wsmp.unusual.mem.{Mem, MemManager}
import com.chinawiserv.wsmp.websocket.WSClient
import com.mongodb.client.model.{Aggregates, BsonField}
import org.bson.Document
import org.bson.conversions.Bson

class UnusualExecutor(val cmds : List[Cmd], val wsClient: WSClient, val memManager: MemManager) extends Runnable {

  private val mongoDBName = "wsmpExt";
  private val mongoColNamePrefix = "Unusual";

  override def run(): Unit = {
    if (cmds != null && cmds.length > 0) {
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
  private def readAndSaveData(cmd: Cmd): List[Array[Short]] = {
    val history = memManager.readData(cmd.id);
    memManager.saveData(cmd);
    return history;
  }

  /**
    * 调用异动算法接口
    * @param current 当前数据
    * @param history 10 条历史数据
    */
  private def computeUnusual(current: Mem, history: List[Array[Short]]): Unit = {
    val res = this.doCompute(current.numOfTraceItems.toInt, current.levels.toArray, history);
    this.analyzeUnusual(current, res);
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
            val currentFreq = 20 + (0.025 * i) * 1000;
            val currentLevel = current.levels(i);
            val currentDate = current.scanOverTime * 1000;
            val doc = new Document();
            doc.put("freq", currentFreq.toInt);
            doc.put("level", currentLevel);
            doc.put("dt", currentDate);
            docs.add(doc);
          }
        }
        MongoDB.mc.insert(mongoDBName, mongoColNamePrefix+current.id, docs, null);
        val amount = this.countByColName(mongoColNamePrefix+current.id);
        if (amount > 0) {
          val doc = new Document();
          doc.put("id", current.id);
          doc.put("un", amount);
          doc.put("flat", current.flat);
          doc.put("flon", current.flon);
          MongoDB.mc.insert(mongoDBName, mongoColNamePrefix+"Levels", doc, null);
          this.sendToWebSocket(current.id, amount, current.flat, current.flon);
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
    pipeline.add(Aggregates.group(null, new BsonField("count", new Document("$sum", 1))))
    pipeline.add(Aggregates.project(new Document("_id", 0)))
    val list: util.List[Document] = MongoDB.mc.aggregate(mongoDBName, colName, pipeline)
    if (list != null && !list.isEmpty) {
      list.get(0).get("count").toString.toInt;
    }
    else {
      0;
    }
  }

  /**
    * 执行实时异动计算
    * @param numOfTraceItems 频率数量
    * @param current 当前消息
    * @param history 10条历史消息
    * @return 异动计算结果
    */
  def doCompute(numOfTraceItems: Int, current: Array[Short], history: List[Array[Short]]): Array[Byte] = {
    synchronized({
      val unusualLevel = new UnusualLevel(numOfTraceItems);
      unusualLevel.CalcUnusualLevel(current, null, numOfTraceItems);
      history.foreach(x => {
        unusualLevel.CalcUnusualLevel(x, null, numOfTraceItems);
      });
      val res = unusualLevel.Res;
      unusualLevel.FreeOccObj();
      return res;
    });
  }

  /**
    * 将实时异动计算结果发送到WebSocket
    * @param id 监测点编号
    * @param amount 异动次数
    */
  private def sendToWebSocket(id: Int, amount: Int, flat: Double, flon: Double): Unit = {
    println("sendToWebSocket="+id+","+amount+","+flat+ ","+flon);
  }
}
