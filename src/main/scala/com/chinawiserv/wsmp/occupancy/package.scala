package com.chinawiserv.wsmp

import com.chinawiserv.wsmp.mongodb.MongoDB
import com.chinawiserv.wsmp.util.DateTime
import com.mongodb.client.model.Filters
import com.chinawiserv.wsmp.configuration.SpringContextManager._;

import scala.collection.JavaConversions
import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * Created by zengpzh on 2017/1/8.
  */
package object occupancy {

  private[occupancy] val TIME_FORMAT = "yyyyMMdd";

  private[occupancy] val TIME_YEAR_LENGTH = 4;

  private[occupancy] val TIME_DAY_LENGTH = 4;

  private[occupancy] val db = "wsmp";

  private[occupancy] val collection_prefix = "occupancy_";

  //实例化该类时就加载磁盘数据到内存，避免延迟加载对第一次调用的影响
  private[occupancy] val OCCUPANCY_MEM = load;

  private val mongoDB  = getBean(classOf[MongoDB]);

  private def load: Map[String, Map[Int, ArrayBuffer[Short]]] = {
    println(DateTime.getCurrentDate_YYYYMMDDHHMMSS);
    val OCCUPANCY_MEM_DATA = Map[Int, ArrayBuffer[Short]]();
    val time = DateTime.getCurrentDate_YYYYMMDDWithOutSeparator;
    val year = time.take(TIME_YEAR_LENGTH);
    val daytime = time.takeRight(TIME_DAY_LENGTH);
    val collection = collection_prefix + year;
    val filter = Filters.eq("time", daytime);
    val records = JavaConversions.asScalaBuffer(mongoDB.mc.find(db, collection, filter, null)).toArray;
    records.foreach(record => {
      val station = record.getInteger("station").toInt;
      val maxLevels = ArrayBuffer[Short]();
      maxLevels ++= JavaConversions.asScalaBuffer[Int](record.get("maxLevels", classOf[java.util.List[Int]])).map(_.toShort);
      OCCUPANCY_MEM_DATA += (station -> maxLevels);
    });
    println(DateTime.getCurrentDate_YYYYMMDDHHMMSS);
    Map[String, Map[Int, ArrayBuffer[Short]]](time -> OCCUPANCY_MEM_DATA);
  }

}
