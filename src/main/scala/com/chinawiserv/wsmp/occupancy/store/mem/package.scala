package com.chinawiserv.wsmp.occupancy
package store

import com.chinawiserv.wsmp.mongodb.MongoDB
import com.chinawiserv.wsmp.util.DateTime
import com.mongodb.client.model.Filters
import org.apache.commons.lang.StringUtils
import org.bson.Document

import scala.collection.JavaConversions
import scala.collection.mutable.{ArrayBuffer, ListBuffer, Map}

/**
  * Created by zengpzh on 2017/1/10.
  */
package object mem {

  private[occupancy] def load: Map[String, Map[Int, ArrayBuffer[Short]]] ={
    println(DateTime.getCurrentDate_YYYYMMDDHHMMSS);
    val OCCUPANCY_MEM_DATA = Map[Int, ArrayBuffer[Short]]();
    val time =  DateTime.getCurrentDate_YYYYMMDDWithOutSeparator;
    val year = time.take(TIME_YEAR_LENGTH);
    val daytime = time.takeRight(TIME_DAY_LENGTH);
    val collection = disk.collection_prefix + year;
    val filter = Filters.eq("time", daytime);
    val records = JavaConversions.asScalaBuffer(MongoDB.mc.find(disk.db, collection, filter, null)).toArray;
    records.foreach(record => {
      val station = record.getInteger("station").toInt;
      val maxLevels = ArrayBuffer[Short]();
      maxLevels ++= JavaConversions.asScalaBuffer[Int](record.get("maxLevels", classOf[java.util.List[Int]])).map(_.toShort);
      OCCUPANCY_MEM_DATA += (station -> maxLevels);
    });
    println(DateTime.getCurrentDate_YYYYMMDDHHMMSS);
    Map[String, Map[Int, ArrayBuffer[Short]]](time -> OCCUPANCY_MEM_DATA);
  }

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
