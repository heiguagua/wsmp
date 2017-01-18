package com.chinawiserv.wsmp

import com.chinawiserv.wsmp.util.DateTime
import com.mongodb.client.model.Filters
import org.bson.Document
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConversions
import scala.collection.mutable.Map

/**
  * Created by zengpzh on 2017/1/8.
  */
package object occupancy {

  private val logger: Logger = LoggerFactory.getLogger(classOf[Occupancy]);

  private[occupancy] val TIME_FORMAT = "yyyyMMdd";

  private[occupancy] val TIME_YEAR_LENGTH = 4;

  private[occupancy] val TIME_DAY_LENGTH = 4;

  private[occupancy] val SHARD_SIZE: Int = 500;

  private[occupancy] val collection_prefix = "occupancy_";

  //实例化该类时就加载磁盘数据到内存，避免延迟加载对第一次调用的影响
  private[occupancy] val OCCUPANCY_MEM = load;

  private def load: Map[String, Map[Int, Array[Byte]]] = {
    try {
      logger.info("Load occupancy data from disk, start: {} ", DateTime.getCurrentDate_YYYYMMDDHHMMSS);
      val OCCUPANCY_MEM_DATA = Map[Int, Array[Byte]]();
      val time = DateTime.getCurrentDate_YYYYMMDDWithOutSeparator;
      val year = time.take(TIME_YEAR_LENGTH);
      val daytime = time.takeRight(TIME_DAY_LENGTH);
      val collection = collection_prefix + year;
      val filter = Filters.eq("time", daytime);
      mongoDB.shardCollection(collection, new Document("station", 1));
      val records = JavaConversions.asScalaBuffer(mongoDB.mc.find(mongoDB.dbName, collection, filter, null)).toArray;
      records.foreach(record => {
        val station = record.getInteger("station").toInt;
        OCCUPANCY_MEM_DATA += (station ->
          JavaConversions.asScalaBuffer[Int](record.get("maxLevels", classOf[java.util.List[Int]])).map(_.toByte).toArray);
      });
      logger.info("Load occupancy data from disk, end: {} ", DateTime.getCurrentDate_YYYYMMDDHHMMSS);
      Map[String, Map[Int, Array[Byte]]](time -> OCCUPANCY_MEM_DATA);
    }
    catch {
      case e: Exception => Map[String, Map[Int, Array[Byte]]]();
    }
  }

}
