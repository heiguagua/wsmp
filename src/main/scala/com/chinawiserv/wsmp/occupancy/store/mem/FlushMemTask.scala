package com.chinawiserv.wsmp.occupancy.store.mem

import com.chinawiserv.wsmp.model.Cmd

/**
  * Created by zengpzh on 2017/1/7.
  */
private[occupancy] class FlushMemTask(cmds: List[Cmd]) extends Runnable{

  override def run(): Unit = {
    synchronized({
      FlushMem.flush(cmds);
    });
  }

}
