package com.chinawiserv.wsmp
package occupancy
package store.disk

/**
  * Created by zengpzh on 2017/1/6.
  */
private class FlushDiskTask extends Runnable {

  override def run(): Unit = {
    while (!Thread.currentThread().isInterrupted) {
      try {
        FlushDisk.flush;
      } catch {
        case e: Exception => Thread.currentThread().interrupt();
      }
    }
  }

}
