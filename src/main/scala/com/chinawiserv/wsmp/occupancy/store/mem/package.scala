package com.chinawiserv.wsmp.occupancy
package store

import org.apache.commons.lang.StringUtils
import org.bson.Document

import scala.collection.mutable.{ArrayBuffer, ListBuffer, Map}

/**
  * Created by zengpzh on 2017/1/10.
  */
package object mem {

  private[occupancy] def getOccupancyRate(time: String, thresholdVal: Short): List[Document] = {
    val records = new ListBuffer[Document]();
    if (StringUtils.isNotBlank(time) && time.length == (TIME_YEAR_LENGTH + TIME_DAY_LENGTH)) {
      val daytime = time.takeRight(TIME_DAY_LENGTH);
      val OCCUPANCY_MEM_DATA = OCCUPANCY_MEM.toMap.getOrElse(time, Map[Int, ArrayBuffer[Short]]());
      for((station, maxLevels) <- OCCUPANCY_MEM_DATA if maxLevels != null){
        val totalNum = maxLevels.length;
        val occupancyNum = maxLevels.count(_ > thresholdVal).toDouble;
        val occupancyRate = occupancyNum / totalNum;
        records += new Document("station", station).append("time", daytime).append("occupancyRate", occupancyRate);
      }
    }
    records.sortWith((a, b) => {
      val stationA = a.getInteger("station");
      val stationB = b.getInteger("station");
      stationA != null && stationB != null && stationA < stationB;
    }).toList;
  }

}
