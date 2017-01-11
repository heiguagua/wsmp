package com.chinawiserv.wsmp.unusual

import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.operator.Operator
import com.chinawiserv.wsmp.unusual.mem.{Mem, MemManager, Web}
import com.chinawiserv.wsmp.websocket.WSClient
import com.codahale.jerkson.Json

import scala.collection.mutable.ArrayBuffer;

class UnusualExecutor(val cmds : List[Cmd], val wsClient: WSClient, val memManager: MemManager) extends Runnable {

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
  private def readAndSaveData(cmd: Cmd): List[Mem] = {
    val history = memManager.readData(cmd.id);
    memManager.saveData(cmd);
    return history;
  }

  /**
    * 调用异动算法接口
    * @param current 当前数据
    * @param history 10 条历史数据
    */
  private def computeUnusual(current: Mem, history: List[Mem]): Unit = {
    val numsOfUnusual =  100;
    val currentArrayBuffer = new ArrayBuffer[Short]();
    current.levels.foreach(x => {
      currentArrayBuffer += x.toShort;
    });
    this.doCompute(current.numOfTraceItems.toInt, currentArrayBuffer.toArray, null);
    this.sendToWebSocket(Web(current.id, numsOfUnusual));
  }

  private def sendToWebSocket(web: Web): Unit = {
    val json = Json.generate[Web](web);
    wsClient.sendMessage(json);
    println("sendToWebSocket="+json);
  }

  def doCompute(numOfTraceItems: Int, current: Array[Short], history: List[Array[Short]]): Array[Char] = {
    synchronized({
      val unusualLevel = new UnusualLevel(numOfTraceItems);
      unusualLevel.CalcUnusualLevel(current, null, numOfTraceItems);
      history.foreach(x => {
        unusualLevel.CalcUnusualLevel(x, null, numOfTraceItems);
      });
      val res = unusualLevel.Res;
      unusualLevel.FreeOccObj();
      res;
    });
  }
}
