package com.chinawiserv.wsmp.unusual

import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.operator.Operator
import com.chinawiserv.wsmp.unusual.mem.{Mem, MemManager, Web}
import com.chinawiserv.wsmp.websocket.WSClient
import com.codahale.jerkson.Json;

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

  private def readAndSaveData(cmd: Cmd): List[Mem] = {
    val history = memManager.readData(cmd.id);
    memManager.saveData(cmd);
    return history;
  }

  private def computeUnusual(current: Mem, history: List[Mem]): Unit = {
    val numsOfUnusual =  100;
    this.sendToWebSocket(Web(current.id, numsOfUnusual));
  }

  private def sendToWebSocket(web: Web): Unit = {
    val json = Json.generate[Web](web);
    wsClient.sendMessage(json);
    println(json);
  }
}
