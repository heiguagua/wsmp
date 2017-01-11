package com.chinawiserv.wsmp.unusual

import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.operator.Operator
import com.chinawiserv.wsmp.unusual.mem.{Mem, MemManager, Web}
import com.chinawiserv.wsmp.websocket.WSClient
import com.codahale.jerkson.Json

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
    this.computeRes(current, res);
  }

  private def computeRes(current: Mem, res: Array[Byte]): Unit = {
    if (current != null && res != null) {
      val amount = res.count(_ == 1);
      if (amount > 0) {
        for (i <- 0.until(res.length)) {
          if (res(i) == 1) {
            val currentFreq = 20 + (0.025 * (i + 1));
            val currentDate = current.scanOverTime * 1000;
            val currentLevel = current.levels(i);
            val unusualTimes = 1;
            println("currentFreq="+currentFreq+", currentDate="+currentDate+", currentLevel="+currentLevel + ", unusualTimes="+unusualTimes);
          }
        }
      }
    }
  }

  def doCompute(numOfTraceItems: Int, current: Array[Short], history: List[Array[Short]]): Array[Byte] = {
    synchronized({
      val unusualLevel = new UnusualLevel(numOfTraceItems);
      unusualLevel.CalcUnusualLevel(current, null, numOfTraceItems);
      println("history.size="+history.size);
      history.foreach(x => {
        unusualLevel.CalcUnusualLevel(x, null, numOfTraceItems);
      });
      val res = unusualLevel.Res;
      unusualLevel.FreeOccObj();
      return res;
    });
  }

  private def sendToWebSocket(web: Web): Unit = {
    val json = Json.generate[Web](web);
    wsClient.sendMessage(json);
    println("sendToWebSocket="+json);
  }
}
