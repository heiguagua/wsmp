package com.chinawiserv.wsmp.occupancy.store.mem

/**
  * Created by zengpzh on 2017/1/7.
  */
private[occupancy] class FlushMemTask extends Runnable{

  override def run(): Unit = {
    while (!Thread.currentThread().isInterrupted){
      try{
        FlushMem.flush;
      }catch {
        case e: Exception => Thread.currentThread().interrupt();
      }
    }

  }

}
