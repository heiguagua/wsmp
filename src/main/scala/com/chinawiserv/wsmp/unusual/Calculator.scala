package com.chinawiserv.wsmp.unusual

import com.chinawiserv.wsmp.UnusualLevel
import com.chinawiserv.wsmp.unusual.mem.Mem

object Calculator {

  def compute(numOfTraceItems: Int, current: Mem, history: List[Mem]): Unit = {
    val unusualLevel = new UnusualLevel(numOfTraceItems);
    for (i <- 0.until(11)) {
      unusualLevel.calcUnusualLevel(null, null ,0);
    }
    println(unusualLevel.Res);
    unusualLevel.free();

  }

}
