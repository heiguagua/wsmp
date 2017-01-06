package com.chinawiserv.wsmp.unusual.handler

import com.chinawiserv.wsmp.mem.MemManager;
import com.chinawiserv.wsmp.spark.model.{Cmd, Mem};
import com.chinawiserv.wsmp.spark.operator.Operator;

class CustomWorker(val memManager: MemManager, val cmds : List[Cmd]) extends Runnable {

  override def run(): Unit = {
    if (cmds != null && cmds.length > 0) {
      cmds.foreach(cmd => {
        val current = Operator.toMem(cmd);
        val history = this.readAndSaveData(cmd);
        this.computeUnusual(current, history);
      });
    }
  }

  def readAndSaveData(cmd: Cmd): List[Mem] = {
    val history = memManager.readData(cmd.id);
    memManager.saveData(cmd);
    return history;
  }

  def computeUnusual(current: Mem, history: List[Mem]): Unit = {
    val numsOfUnusual =  100;
    this.memManager.updateWeb(current.id, numsOfUnusual);
    val json = this.memManager.jsonWeb();
    this.sendToWebSocket(json);
  }

  def sendToWebSocket(json: String): Unit = {
    println("sendToWebSocket");
  }
}
