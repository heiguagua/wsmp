package com.chinawiserv.wsmp.spark.operator

import com.chinawiserv.wsmp.spark.model.Cmd

object Printer {

  def printCmd(cmd: Cmd): Unit = {
    if (cmd != null) {
      println("开始标志="+cmd.uiFlag);
      println("数据帧长度="+cmd.uiLength);
      println("本类型数据长度="+cmd.dataUiLength);
      println("数据类型="+cmd.cdataType);
      println("传感器编号="+cmd.id);
      println("本地的格林威治时间的秒数="+cmd.scanOverTime);
      println("开始频率="+cmd.startFreq);
      println("结束频率="+cmd.stopFreq);
      println("扫描步进="+cmd.stepFreq);
      println("传感器经度="+cmd.flon);
      println("传感器纬度="+cmd.flat);
      println("忽略="+cmd.compressInfo);
      println("数据个数="+cmd.numOfTraceItems);
      println("数据类型="+cmd.selectorFlags);
      println("值固定="+cmd.attCount);
      println("数据长度="+cmd.dataLen);
      println("能量数据="+cmd.levels.length);
      println("统计值="+cmd.stddevs.length);
    }
  }

}
