package com.chinawiserv.wsmp.occupancy.store

import com.chinawiserv.wsmp.mongodb.MongoDB
import com.chinawiserv.wsmp.util.DateTime
import com.mongodb.client.model.Filters

import scala.collection.JavaConversions
import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * Created by Administrator on 2017/1/10.
  */
package object mem {

  private[occupancy] def load: Map[String, Map[Int, ArrayBuffer[Byte]]] ={
    println(DateTime.getCurrentDate_YYYYMMDDHHMMSS);
    val OCCUPANCY_MEM_DATA = Map[Int, ArrayBuffer[Byte]]();
    val time =  DateTime.getCurrentDate_YYYYMMDDWithOutSeparator;
    val year = time.take(4);
    val daytime = time.takeRight(4);
    val collection = disk.collection_prefix + year;
    val filter = Filters.eq("time", daytime);
    disk.checkCollection(collection);
    val records = JavaConversions.asScalaBuffer(MongoDB.mc.find(disk.db, collection, filter, null)).toArray;
    records.foreach(record => {
      val station = record.getInteger("station").toInt;
      val maxLevels = ArrayBuffer[Byte]();
      maxLevels ++= JavaConversions.asScalaBuffer[Int](record.get("maxLevels", classOf[java.util.List[Int]])).map(_.toByte);
      OCCUPANCY_MEM_DATA += (station -> maxLevels);
    });
    println(DateTime.getCurrentDate_YYYYMMDDHHMMSS);
    Map[String, Map[Int, ArrayBuffer[Byte]]](time -> OCCUPANCY_MEM_DATA);
  }

}
