package com.chinawiserv.wsmp.occupancy.flush.disk

import com.chinawiserv.wsmp.mongodb.MongoDB
import com.chinawiserv.wsmp.util.DateTime
import com.mongodb.client.model.Filters

import scala.collection.JavaConversions
import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * Created by zengpzh on 2017/1/9.
  */
object LoadDisk {

  private[occupancy] def load(): Map[String, Map[Int, ArrayBuffer[Byte]]] ={
    println(DateTime.getCurrentDate_YYYYMMDDHHMMSS);
    val OCCUPANCY_MEM_DATA = Map[Int, ArrayBuffer[Byte]]();
    val time =  DateTime.getCurrentDate_YYYYMMDDWithOutSeparator;
    val year = time.take(4);
    val daytime = time.takeRight(4);
    val collection = collection_prefix + "_" + year;
    val filter = Filters.eq("time", daytime);
    checkCollection(collection);
    val records = JavaConversions.asScalaBuffer(MongoDB.mc.find(db, collection, filter, null)).toArray;
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
